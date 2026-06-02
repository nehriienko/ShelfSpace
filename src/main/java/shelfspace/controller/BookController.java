package shelfspace.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import shelfspace.dto.BookRequestDto;
import shelfspace.dto.BookResponseDto;
import shelfspace.service.BookService;

import java.util.List;

@RestController     // вказує Spring, що цей клас є REST-контролером. автоматично серіалізує відповіді методів у формат JSON.
@RequestMapping("/api/books")     // задає базовий URL-шлях для всіх ендпоінтів цього контролера.
public class BookController {

    // впровадження залежності  через інтерфейс сервісу. final гарантує незмінність залежності після ініціалізації.
    private final BookService service;

    // конструктор для впровадження залежностей
    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping    // обробляє HTTP POST-запити для створення нового ресурсу.
    public BookResponseDto create(@Valid @RequestBody BookRequestDto dto) { // @Valid перевіряє обмеження в BookRequestDto
        // @RequestBody вказує Spring десеріалізувати тіло HTTP-запиту (JSON) в об'єкт Java (BookRequestDto).
        return service.create(dto);
    }

    @GetMapping // обробляє HTTP GET-запити на базовий URL
    public List<BookResponseDto> getAll() {
        return service.getAll(); // повертаємо список усіх книг, трансформованих у DTO
    }

    @GetMapping("/{id}") // обробляє GET-запити з динамічним параметром id у URL
    public BookResponseDto getById(@PathVariable Long id) { // @PathVariable зв'язує змінну {id} із параметром методу 'id'.
        return service.getById(id); // запитуємо у сервісу конкретну книгу за її унікальним ідентифікатором
    }


    @PutMapping("/{id}") // обробляє HTTP PUT-запити для повного оновлення існуючого ресурсу за його id.
    public BookResponseDto update(@PathVariable Long id, @Valid @RequestBody BookRequestDto dto) {
        return service.update(id, dto); // передаємо ідентифікатор та нові дані для оновлення в сервіс
    }

    @DeleteMapping("/{id}") // обробляє HTTP DELETE-запити для видалення ресурсу за його id.
    public void delete(@PathVariable Long id) {
        service.delete(id); // викликаємо метод видалення в сервісному шарі. Метод повертає статус void
    }
}