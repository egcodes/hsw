# HSW - BE service

This project is BackEnd service for new website which i will publish soon.

## Used libraries
* spring-boot-starter-web
* spring-boot-starter-data-jpa
* spring-boot-starter-validation
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
$ mvn clean package

$ java -jar target/docker-message-server-1.0.0.jar

$ docker build --tag=hsw-server:latest .

$ docker run -p8080 --name hsw-server hsw-server:latest