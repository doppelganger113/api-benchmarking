# Go server with Chi router

## Building Docker image

  ```bash
  # Build
  docker build . -t go-todo
  # Start
  docker run -p 3000:3000 -e DB_URL="$DB_URL" -d go-todo
  # Stop
  docker stop go-todo
  # Remove
  docker rm -v go-todo 
  ```

## Troubleshooting
- Error when building: `open /usr/local/go/pkg/darwin_amd64/runtime/cgo.a: permission denied`
   ```bash
   sudo chmod -R 777 /usr/local/go
   ```
