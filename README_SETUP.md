# Setting things up

## 1. Create EC2 instance

Via AWS console

1. Before launching a new EC2 instance, create IAM Role `ec2-cloudwatch-agent-role` that will have the policy `CloudWatchAgentServerPolicy`, we will use this for cloud watch agent
3. Launch an Ubuntu x86 64bit instance, set previously created IAM role and **allow traffic on all ports**

## 2. Add AWS memory monitoring agent

Connect to the EC2 instance and issue following commands to set the agent so that we can track memory usage

1. Download the agent
```bash
sudo apt-get update
wget https://s3.amazonaws.com/amazoncloudwatch-agent/ubuntu/amd64/latest/amazon-cloudwatch-agent.deb
sudo dpkg -i -E ./amazon-cloudwatch-agent.deb
```
2. Run the configuration wizard
```bash
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-config-wizard
```

Example of the configuration file that is stored at `/opt/aws/amazon-cloudwatch-agent/bin/config.json`:
```json
{
  "agent": {
    "metrics_collection_interval": 1,
    "run_as_user": "root"
  },
  "metrics": {
    "metrics_collected": {
      "cpu": {
        "measurement": [
          "cpu_usage_idle",
          "cpu_usage_iowait",
          "cpu_usage_user",
          "cpu_usage_system"
        ],
        "metrics_collection_interval": 1,
        "totalcpu": false
      },
      "disk": {
        "measurement": [
          "used_percent",
          "inodes_free"
        ],
        "metrics_collection_interval": 1,
        "resources": [
          "*"
        ]
      },
      "diskio": {
        "measurement": [
          "io_time"
        ],
        "metrics_collection_interval": 1,
        "resources": [
          "*"
        ]
      },
      "mem": {
        "measurement": [
          "mem_used_percent"
        ],
        "metrics_collection_interval": 1
      },
      "swap": {
        "measurement": [
          "swap_used_percent"
        ],
        "metrics_collection_interval": 1
      }
    }
  }
}
```
3. Start the agent
```bash
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s -c file:/opt/aws/amazon-cloudwatch-agent/bin/config.json 
```
4. Check the status of the agent
```bash
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -m ec2 -a status 
```



## 3. Installing Docker

```bash
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    acl
    
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

echo \
  "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

# Test it
sudo docker run hello-world

# Enable Docker without sudo on remote machine
sudo setfacl --modify user:ubuntu:rw /var/run/docker.sock
```

## 4. Starting the Postgresql database

**This part will incur additional cost, so do this near the end!** Best time to start is after you verify your docker 
image is working on the server itself. Checkout the [README_DEPLOY.md](./README_DEPLOY.md).

Setup Postgresql on AWS console with the following settings
 - Version: 13.3
 - Size: db.m6g.large (2vCPUs, 8GiB RAM)
 - IOPS: 3000
 - Standard instance (not burstable)
 - **Disable** auto-scaling
 - Multi-AZ deployment: **DO NOT** create a standby instance
 - Public access: YES
