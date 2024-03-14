FROM maven:3.9.6-eclipse-temurin-21 as build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:21-jdk-slim
COPY --from=build /target/ImageToText-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","demo.jar"]