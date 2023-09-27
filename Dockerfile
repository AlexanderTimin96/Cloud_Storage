FROM openjdk:17-alpine
EXPOSE 8081
ADD target/cloudStorage-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]