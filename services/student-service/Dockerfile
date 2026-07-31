# syntax=docker/dockerfile:1

# ==================================
# Stage 1: Build Maven multi-module
# ==================================
FROM maven:3.9.16-eclipse-temurin-17-alpine AS builder

WORKDIR /workspace

# Phải copy toàn bộ Maven multi-module project
# trước khi Maven đọc POM gốc
COPY . .

# Chỉ build student-service và các module mà nó phụ thuộc
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp \
    -pl :student-service \
    -am \
    clean package \
    -DskipTests


# ==================================
# Stage 2: Runtime
# ==================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S spring && \
    adduser -S spring -G spring

COPY --from=builder \
    --chown=spring:spring \
    /workspace/services/student-service/target/student-service.jar \
    /app/app.jar

USER spring:spring

EXPOSE 8082

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]