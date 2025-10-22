# Build stage
FROM gradle:8.10.2-jdk21-alpine AS builder
WORKDIR /workspace
COPY . .
# Build the Spring Boot fat jar
RUN gradle clean bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy built jar (project name: fire-comp-backend, version: 1.0.0)
COPY --from=builder /workspace/build/libs/fire-comp-backend-1.0.0.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
