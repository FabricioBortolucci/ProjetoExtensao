FROM openjdk:22-jdk-slim
VOLUME /tmp
EXPOSE 8080
COPY target/monkeyflip-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]