package shelfspace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {


    // тут ми обробляємо наш  ResourceNotFoundException (наприклад, коли книгу з таким ID не знайдено).
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        // створюємо карту для формування JSON-відповіді
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage()); // витягуємо повідомлення, яке ми передали при виклику винятку

        // ResponseEntity дозволяє повністю налаштувати HTTP-відповідь: її тіло (error) та HTTP-статус (404 NOT FOUND).
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // цей виняток автоматично викидає Spring, коли вхідні дані у DTO не проходять перевірку.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // ex.getBindingResult().getAllErrors() витягує список усіх знайдених порушень валідації
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // приводимо об'єкт помилки до FieldError, щоб дізнатися назву поля, де сталася помилка
            String fieldName = ((FieldError) error).getField();
            // витягуємо повідомлення помилки, яке ми прописали в DTO
            String errorMessage = error.getDefaultMessage();

            // записуємо в мапу: "назва_поля" -> "текст_помилки"
            errors.put(fieldName, errorMessage);
        });

        // повертаємо  мапу з помилками валідації та HTTP-статусом 400 BAD REQUEST.
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}