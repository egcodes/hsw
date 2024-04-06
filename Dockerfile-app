FROM openjdk:21-jdk
MAINTAINER egcodes
COPY target/hsw-1.0.0.jar ./
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/hsw-1.0.0.jar"]
