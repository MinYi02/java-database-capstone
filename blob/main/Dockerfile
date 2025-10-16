# Use official OpenJDK 17 runtime as base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Copy the built JAR file to the working directory
COPY target/*.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Create a non-root user and switch to it for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Run the JAR file
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
