FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/Task_Tracker_API-1.0-SNAPSHOT.jar app.jar
EXPOSE 7070
ENTRYPOINT ["java", "-jar", "app.jar"]