FROM openjdk:17-alpine
LABEL authors="kko"

WORKDIR /sales

COPY build/libs/sales.jar sales.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "sales.jar"]