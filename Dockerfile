FROM adoptopenjdk/openjdk11:ubi
MAINTAINER erdi.gurbuz
COPY target/eg-service-1.0.0.jar application.yml ./
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eg-service-1.0.0.jar", "--spring.config.location=classpath:/application.yml,file:/application.yml"]
