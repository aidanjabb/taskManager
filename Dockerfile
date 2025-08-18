# -------- Build stage --------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# copy pom first and warm up dependency cache
COPY pom.xml .
RUN mvn -B -q -e dependency:go-offline

# now copy sources and build
COPY src ./src
RUN mvn -B -DskipTests package

# -------- Runtime stage --------
FROM eclipse-temurin:17-jre
WORKDIR /app

# copy the built jar from the builder stage
# (adjust the glob if your artifact name/version differ)
COPY --from=builder /app/target/*-SNAPSHOT.jar app.jar

# optional: small-instance memory tuning on Render
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
