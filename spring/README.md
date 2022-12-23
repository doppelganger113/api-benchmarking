# Spring boot vanilla

Threaded server in Java with Spring framework

## Building Docker image

```bash
# Build
mvn clean package
mvn install
docker build . -t spring-todo
# Start
docker run -p 3000:3000 -e SPRING_DATASOURCE_URL="${SPRING_DATASOURCE_URL}" -e SPRING_DATASOURCE_USERNAME="postgres" -e SPRING_DATASOURCE_PASSWORD="${SPRING_DATASOURCE_PASSWORD}" -d spring-todo
# Stop
docker stop spring-todo
# Remove
docker rm -v spring-todo
```

## GraalVM?

Guideline is [here](https://github.com/jonashackt/spring-boot-graalvm)
