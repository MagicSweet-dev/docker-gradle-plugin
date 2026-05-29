FROM bellsoft/liberica-openjdk-alpine-musl:25-cds

ARG JAR_FILE
COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]