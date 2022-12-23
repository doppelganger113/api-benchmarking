# Nest.js API

Since most users will use Nest.js as a framework and go with an ORM, we'll stick with the TypeORM.

## Building Docker image

  ```bash
  # Build
  docker build . -t fastify-nodejs-todo
  # Start
  docker run -p 3000:3000 -e DB_URL="$DB_URL" -d fastify-nodejs-todo
  # Stop
  docker stop fastify-nodejs-todo
  # Remove
  docker rm -v fastify-nodejs-todo 
  ```
