FROM adoptopenjdk/openjdk11:ubi
MAINTAINER egcodes
COPY target/hsw-0.7-SNAPSHOT.jar hsw-0.7-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/hsw-0.7-SNAPSHOT.jar"]
