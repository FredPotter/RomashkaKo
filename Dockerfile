FROM openjdk:17-jdk
WORKDIR /app

COPY target/your-application.jar /app/your-application.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "RomashkaKo-1.0.jar"]
