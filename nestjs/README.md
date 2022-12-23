# Nest.js API

Since most users will use Nest.js as a framework and go with an ORM, we'll stick with the TypeORM.

## Building Docker image

  ```bash
  # Build
  docker build . -t nodejs-todo
  # Start
  docker run -p 3000:3000 -e DB_URL="$DB_URL" -d nodejs-todo
  # Stop
  docker stop nodejs-todo
  # Remove
  docker rm -v nodejs-todo 
  ```
