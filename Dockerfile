# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/Onboarding-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 7777

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
