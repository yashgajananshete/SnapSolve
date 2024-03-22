# Use Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-21 as build
COPY . .
RUN mvn clean package -DskipTests

# Use openjdk image for the final application
FROM openjdk:21-jdk-slim

# Install Tesseract OCR and its dependencies
RUN apt-get update && \
    apt-get install -y tesseract-ocr libtesseract-dev

# Copy the built JAR from the build stage
COPY --from=build /target/ImageToText-0.0.1-SNAPSHOT.jar demo.jar

# Expose the port the application runs on
EXPOSE 8080

# Set environment variable for Tesseract data path
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata

# Run the application
ENTRYPOINT [ "java","-jar","demo.jar"]
