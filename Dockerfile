FROM openjdk:17-jdk
WORKDIR /app

COPY target/RomashkaKo-1.0.jar /app/RomashkaKo-1.0.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "RomashkaKo-1.0.jar"]
