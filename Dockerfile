FROM adoptopenjdk/openjdk11:ubi
MAINTAINER erdi.gurbuz
COPY target/hsw-1.0.0.jar application.yml ./
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/hsw-1.0.0.jar", "--spring.config.location=classpath:/application.yml,file:/application.yml"]
