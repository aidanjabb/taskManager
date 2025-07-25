# Start with an official Java 17 runtime base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/task-manager-api-0.0.1-SNAPSHOT.jar app.jar

# Tell Docker how to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
