# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the builder stage
WORKDIR /app

# Copy only pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean install \
    && rm -rf /root/.m2 \
    && rm -rf /app/target/*.jar.original

# Stage 2: Run the application
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Install necessary packages for handling fonts
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfontconfig1 \
    && rm -rf /var/lib/apt/lists/*

# Copy the built jar from the builder stage
COPY --from=build /app/target/employee-retention-predection-0.0.1-SNAPSHOT.jar ./employee-retention-predection-0.0.1-SNAPSHOT.jar

# Expose the port the app runs on
EXPOSE 8089

# Start the Java application with JVM options
CMD ["java", "-Xmx2g", "-Xms1g", "-Djava.awt.headless=true", "-jar", "liberty-groom.jar"]