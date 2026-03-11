# syntax=docker/dockerfile:1.7

FROM node:20-bookworm-slim AS assets
WORKDIR /workspace

COPY package.json package-lock.json ./
RUN npm ci

COPY tailwind.config.js webpack.config.js ./
COPY src/main/resources/static ./src/main/resources/static

RUN npm run tailwind:build && npm run webpack

FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

COPY src ./src
COPY --from=assets /workspace/target/generated-resources ./target/generated-resources

RUN ./mvnw -DskipTests -Dexec.skip=true package

FROM eclipse-temurin:25-jre
WORKDIR /app

RUN useradd --system --create-home --shell /usr/sbin/nologin appuser

COPY --from=build /workspace/target/lumberlake-0.0.1-SNAPSHOT.jar /app/app.jar

USER appuser
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
