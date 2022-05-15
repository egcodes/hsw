FROM adoptopenjdk/openjdk11:ubi
MAINTAINER egcodes
COPY target/hsw-0.0.1-SNAPSHOT.jar hsw-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/hsw-0.0.1-SNAPSHOT.jar"]
