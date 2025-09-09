# Step 1: Use official JDK as base image
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy the JAR file into container (make sure you've built it first!)
COPY target/event-registration-system-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose port
EXPOSE 8080

# Step 5: Command to run app
ENTRYPOINT ["java", "-jar", "app.jar"]