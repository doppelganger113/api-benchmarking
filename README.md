# api-benchmarking

Yet another performance measurement of HTTP API. In our tests we will test performance of the API on a single CPU and
enough of memory with a more powerful database behind it, so that it doesn't cause a bottle-neck during testing. We will
test with latency and parsing for high concurrency of requests.

## EC2 Configuration

AWS EC2 t2 micro with Ubuntu

- 1 vCPU
- 2 GB Memory

## Service configuration

List of environment variables

| Name   | Required/Optional | Default <br/>                                                                                                      | Description               |
|--------|-------------------|--------------------------------------------------------------------------------------------------------------------|---------------------------|
| PORT   | Optional          | 3000                                                                                                               | Port for the server       |
| DB_URL | Optional          | 'postgresql://postgres:zastotrafozuji@database-1.cqbz8vdev8iv.eu-central-1.rds.amazonaws.com/test?sslmode=disable' | Url for the DB connection |

## Making requests

GET /api/todos?sleep=1000&size=100&transform=true

Parameters

- **sleep**, sets amount of milliseconds for the request to sleep imitating I/O wait time
- **size**, sets the amount of rows to retrieve from the database
- **transform**, if set to true, will transform the array into a different one by performing string concatenation and
  division
- **skipData**, if set to true will avoid database querying, default is false

## Running the benchmark

For information on running and previewing the benchmark check out
[README_BENCHMARKING.md](./README_BENCHMARKING.md)

# Troubleshooting

- Remove dangling Docker images

 ```bash
 docker rmi $(docker images --filter "dangling=true" -q --no-trunc) 
 ```
