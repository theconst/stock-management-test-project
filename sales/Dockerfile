FROM openjdk:17-alpine
LABEL authors="kko"

WORKDIR /sales

COPY build/libs/sales.jar sales.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sales.jar"]