# syntax=docker/dockerfile:1

# =========================
# Giai đoạn 1: Build
# =========================
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom trước để tận dụng cache dependency
COPY pom.xml .

RUN mvn -B dependency:go-offline

# Copy source code
COPY src ./src

# Build JAR
RUN mvn -B clean package -DskipTests


# =========================
# Giai đoạn 2: Runtime
# =========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Tạo user không có quyền root
RUN addgroup -S spring \
    && adduser -S spring -G spring

COPY --from=builder \
     --chown=spring:spring \
     /app/target/*.jar \
     app.jar

USER spring:spring

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]