### Multi-stage Dockerfile for building and running the Spring Boot app
### This image builds the project using the Gradle wrapper and runs the resulting jar.

FROM openjdk:21-jdk-slim AS builder
WORKDIR /workspace

# Copy gradle wrapper and gradle files first for caching
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# Copy rest of the source
COPY . .

# Ensure wrapper is executable
RUN chmod +x ./gradlew || true

# Build the application (skip tests to speed up CI builds; change if you want tests in image builds)
RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
