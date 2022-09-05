FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
COPY /target/marven-1.0.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
