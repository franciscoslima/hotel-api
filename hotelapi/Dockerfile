FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y maven && apt-get clean

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=docker

CMD ["java", "-jar", "target/hotelapi-0.0.1-SNAPSHOT.jar"]
