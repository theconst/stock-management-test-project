FROM openjdk:17-alpine
LABEL authors="kko"

WORKDIR /purchases

COPY build/libs/purchases-boot-*.jar purchases.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "purchases.jar"]