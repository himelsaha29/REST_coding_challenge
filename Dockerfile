FROM openjdk:8
EXPOSE 8080
ADD target/docker.jar docker.jar
ENTRYPOINT ["java", "-jar", "/docker.jar"]