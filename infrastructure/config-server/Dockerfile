# syntax=docker/dockerfile:1

# =========================
# Giai đoạn 1: Build project
# =========================
FROM maven:3.9.16-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom trước để tận dụng Docker cache cho dependency
COPY pom.xml .

RUN mvn -B dependency:go-offline

# Copy source code
COPY src ./src

# Build file JAR, bỏ qua test
RUN mvn -B clean package -DskipTests


# =============================
# Giai đoạn 2: Chạy application
# =============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Tạo user không có quyền root
RUN addgroup -S spring && \
    adduser -S spring -G spring

# Copy file JAR từ giai đoạn build
COPY --from=builder --chown=spring:spring /app/target/*.jar app.jar

USER spring:spring

EXPOSE 8888

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]