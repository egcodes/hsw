FROM adoptopenjdk/openjdk11:ubi
MAINTAINER egcodes
COPY target/hsw-1.0.0.jar ./
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eg-service-1.0.0.jar"]
