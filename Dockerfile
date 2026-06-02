# етап 1: Складання (Build stage)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# копіюємо налаштування проекту та вихідний код
COPY pom.xml .
COPY src ./src
# збираємо jar-файл програми, пропускаючи тести для швидкості складання
RUN mvn clean package -DskipTests

# етап 2: Запуск (Run stage)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# забираємо тільки готовий зібраний jar-файл із першого етапу
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# команда для запуску програми
ENTRYPOINT ["java", "-jar", "app.jar"]