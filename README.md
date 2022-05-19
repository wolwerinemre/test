# Task (Kafka communication)
Mandatory to use: Spring Boot, Gradle or Maven, Kafka, MySQL.
The goal is to implement a Spring Boot multi-module application which receives a request via REST API, publishes it to Kafka and after
consumption writes it to MySQL

## Run the System
We can easily run the whole with only a single command:
```bash
docker-compose up
```

Docker will pull the MySQL and Spring Boot images (if our machine has it before).
The services can be run on the background with command:
```bash
docker-compose up -d
```

If you dont have images please run the maven command before the docker compose:
```bash
mvn clean:package
```
## Stop the System
Stopping all the running containers is also simple with a single command:
```bash
docker-compose down
```
## Swagger and Kafka UI
- Kafka UI - you can use to monitor the kafka message -> localhost:9000
- Swagger UI - you can use to call the controller -> localhost:8081/swagger-ui.html
