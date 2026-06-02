# Shelfspace API

Веб-додаток  для ведення особистої книжкової полиці, відстеження прочитаного та збереження відгуків із оцінками.

## Технологічний стек
* **Backend:** Java 17, Spring Boot (Web, Data JPA, Validation)
* **Database:** PostgreSQL
* **Documentation:** Swagger UI (OpenAPI 3)
* **Testing:** JUnit 5, Mockito (Unit & Slice Integration Testing)
* **DevOps:** Docker, Docker Compose (Multi-stage Build), Render.com

## Архітектура та інженерні практики
Проєкт реалізовано з дотриманням найкращих практик розробки на Spring Boot:
* **Шарова архітектура (Layered Architecture):** чіткий поділ на `Controller` ➡️ `Service` ➡️ `Repository`.
* **Використання DTO:** розділено класи сутностей БД (`Book`) та об'єкти передачі даних для запитів (`BookRequestDto`) і відповідей (`BookResponseDto`).
* **Constructor Injection:** відмова від `@Autowired` на користь інжекції залежностей через конструктори.
* **Глобальна обробка помилок:** реалізовано `@ControllerAdvice` (`GlobalExceptionHandler`) для кастомного перехоплення 404 (Not Found) та 400 (Bad Request) помилок.
* **Валідація даних:** вхідні дані проходять жорстку перевірку (Jakarta Validation) на наявність пустих полів та коректність рейтингу (від 1 до 10).

## Запуск проєкту локально

### 1. Клонування репозиторію

### 2. Запуск через Docker Compose
Для запуску всього оточення (Spring Boot додаток + база даних PostgreSQL) однією командою: docker-compose up --build
Після запуску додаток буде доступний за адресою: http://localhost:8080
### 3. Документація API (Swagger UI)
Інтерактивна документація та тестування ендпоінтів:
http://localhost:8080/swagger-ui/index.html

**Тестування**
У проєкті реалізовано покриття коду тестами:
* **Unit-тести:** ізольоване тестування бізнес-логіки сервісів за допомогою Mockito.
* **Integration-тести:** перевірка роботи веб-шару, валідації та обробки помилок за допомогою @WebMvcTest та MockMvc.

**Запуск тестів локально:** mvn test 

**Публічний Деплой**
Застосунок задеплоєно на платформі Render.com та він доступний за посиланням:
Live URL: https://shelfspace-api.onrender.com
Live Swagger UI: https://shelfspace-api.onrender.com/swagger-ui/index.html