# Building and deploying docker images to EC2

Ensure that you have added the ssh key (one used for EC2) to your ssh agent on your machine, so that we can connect 
Docker with the EC2.

```bash
ssh-add -K ~/.ssh/your-pem.pem
```

Create docker context and switch to the remote, this way we can send build context to the remote and start the app there

```bash
# Register remote context
docker context create remote --docker "host=ssh://ubuntu@18.185.249.125"

# If you already had a previous one, remove it with the following command:
# docker context remove remote

# Switch context to use remote
docker context use remote
docker ps
```

Now when you execute commands like build, it will send the build context to the remote machine and make deploying and
running these images to EC2 easier.

## Requirements

- Node.js
- Go
- Docker

## Database and migration

Set the database by running Nest.js, which will create the schema, then execute within `nestjs/` directory:

```bash
export DB_URL="postgresql://user:1234@localhost:5432/test?sslmode=disable"
# Start app locally to create entities, then kill it
npm run start:dev 
# Run the migration to seed the data into the database
# Due to ORM issues, you'll have to replace the:
# migrations: [path.resolve(__dirname, 'migrations/**/*.ts')]
# with
# migrations: ['migrations/**/*.ts']
# Then, revert it back when building the image
npm run migration
```

This will populate the DB with 5000 rows of todos

## Building the image

Check directories for each service

## Candidates

- Node.js [Nest.js](./nestjs/README.md) + TypeORM
- Node.js [Nest.js](./nestjs/README.md) + TypeORM + Fastify server
- Go + Chi router + PGX

## Size of images

```bash
api-benchmarking_go-todo                  latest           29.2MB
api-benchmarking_nestjs-todo              latest           159MB
api-benchmarking_fastify-nestjs-todo      latest           166MB
```
