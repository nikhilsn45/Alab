FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/ALabs-0.0.1-SNAPSHOT.jar ALabs-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "ALabs-0.0.1-SNAPSHOT.jar" ]