# Hacker's Work BE Service

Site: https://hackerswork.com

<img src="architecture.png">

## Used libraries
* spring-boot-starter-web
* spring-boot-starter-data-jpa
* spring-boot-starter-validation
* spring-boot-starter-security
* spring-boot-starter-cache
* spring-boot-starter-test
* springfox-swagger-ui
* mapstruct
* h2
* Lombok
* rest-assured
* shedlock
* postgresql

## Follow the below steps to containerize the application

```shell
# Build the project
$ mvn clean package

# Build Docker image
$ docker build --tag=hsw:latest .

# Run App, DB, HttpServer
$ docker-compose up -d
```

## Test Users

UserName: testuser, Password: 12345678

UserName: hackers-work, Password: 12345678