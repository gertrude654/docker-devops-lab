# Use an official OpenJDK 22 runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the host into the container
#COPY target/DockerLab-0.0.1-SNAPSHOT.jar /app/DockerLab.jar
COPY target/DockerLab-0.0.1-SNAPSHOT.jar /app/DockerLab.jar


# Expose the port the Spring Boot app runs on
EXPOSE 5050

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/DockerLab.jar"]
