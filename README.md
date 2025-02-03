## Description

Demo blog web-application based on Spring Boot framework

## Compile and run the project

Git bash:
```bash
# build
$ ./mvnw clean install

# run
$ java -jar target/demo-blog-0.0.1-SNAPSHOT.jar
```

Command promt:
```bash
# build
mvn clean install

# run
java -jar target/demo-blog-0.0.1-SNAPSHOT.jar
```
Docker:
```bash
# Image build
docker build -t demo-blog .

# run
docker run -p 8080:8080 demo-blog
```

## API Documentation

OpenAPI descriptions - <domain-name>/v3/api-docs
```
http://localhost:8080/v3/api-docs
```
Swagger UI - <domain-name>/swagger-ui/index.html
```
http://localhost:8080/swagger-ui/index.html
```

## Scenario
1. **Create user:** POST /api/user
2. **Create post(s):** POST /api/post
3. **Manipulate posts:** Retrieve filtered posts by date, update posts, or delete them
