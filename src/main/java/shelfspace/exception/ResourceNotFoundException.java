package shelfspace.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { // конструктор, який приймає кастомне текстове повідомлення про помилку
        super(message); // передаємо це повідомлення вище в конструктор базового класу RuntimeException за допомогою super().
        // звідти наш GlobalExceptionHandler зможе легко витягнути його через метод ex.getMessage().
    }
}