FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S appuser && adduser -S appuser -G appuser

# Copy the JAR built by Maven
COPY target/E_Learning_Platform-0.0.1-SNAPSHOT.jar app.jar

RUN chown -R appuser:appuser /app
USER appuser

EXPOSE 8085

HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8085/actuator/health/readiness || exit 1

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "-XX:+ExitOnOutOfMemoryError", "-jar", "app.jar"]
