# Spring boot webflux

Reactive server in Java with Spring framework

## Building Docker image

```bash
# Build
mvn clean package
mvn install
docker build . -t spring-reactive-todo
# Start
docker run -p 3000:3000 -e SPRING_R2DBC_URL="${SPRING_R2DBC_URL}" -d spring-reactive-todo 
# Stop
docker stop spring-reactive-todo
# Remove
docker rm -v spring-reactive-todo
```

## GraalVM?

Guideline is [here](https://github.com/jonashackt/spring-boot-graalvm)
