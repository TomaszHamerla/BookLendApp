FROM openjdk:20

WORKDIR /app

COPY target/BookLendApp-0.0.1-SNAPSHOT.jar app.jar

COPY BookLendApp.mv.db BookLendApp.mv.db

EXPOSE 8080


CMD ["java", "-jar", "app.jar"]
