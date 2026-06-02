package shelfspace.service;

import org.springframework.stereotype.Service;
import shelfspace.dto.BookRequestDto;
import shelfspace.dto.BookResponseDto;
import shelfspace.entity.Book;
import shelfspace.exception.ResourceNotFoundException;
import shelfspace.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;  // впроваджуємо репозиторій для безпосередньої роботи з базою даних через JPA.

    // впровадження залежності через конструктор — гарантує, що сервіс не запуститься без валідного репозиторію.
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookResponseDto create(BookRequestDto dto) {
        // створюємо чисту сутність бази даних Book і заповнюємо її даними, які прийшли у форматі Request DTO.
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setRating(dto.getRating());
        book.setReview(dto.getReview());

        // зберігаємо сутність у БД. Метод save() повертає новий об'єкт Book, але вже згенерованим базою даних унікальним ID.
        Book savedBook = repository.save(book);

        // переносимо дані зі збереженої сутності (з ID) у вихідний об'єкт Response DTO, який ми відправимо клієнту.
        BookResponseDto response = new BookResponseDto();
        response.setId(savedBook.getId());
        response.setTitle(savedBook.getTitle());
        response.setAuthor(savedBook.getAuthor());
        response.setRating(savedBook.getRating());
        response.setReview(savedBook.getReview());

        return response;
    }

    @Override
    public List<BookResponseDto> getAll() {
        return repository.findAll() // дістає з бази список усіх книг у вигляді List<Book>.
                .stream() // перетворюємо список на потік даних (Stream API) для швидкої обробки.
                .map(book -> {
                    // метод .map() трансформує кожен об'єкт сутності Book у об'єкт BookResponseDto.
                    BookResponseDto dto = new BookResponseDto();
                    dto.setId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setRating(book.getRating());
                    dto.setReview(book.getReview());
                    return dto;
                })
                .toList(); // збираємо трансформований потік назад у фінальний List<BookResponseDto>.
    }

    @Override
    public BookResponseDto getById(Long id) {
        // findById() повертає об'єкт Optional<Book>.
        // .orElseThrow() - якщо книга є, вона повертається, якщо книги в базі немає - автоматично викидається наш ResourceNotFoundException.
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setRating(book.getRating());
        dto.setReview(book.getReview());

        return dto;
    }

    @Override
    public BookResponseDto update(Long id, BookRequestDto dto) {
        // перевіряємо, чи взагалі існує книга, яку хочуть оновити.
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // оновлюємо поля існуючої сутності новими даними з Request DTO.
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setRating(dto.getRating());
        book.setReview(dto.getReview());

        // зберігаємо оновлену книгу, оскільки у об'єкта 'book' вже є ID, Hibernate розуміє, що треба виконати SQL-команду UPDATE, а не INSERT.
        Book updatedBook = repository.save(book);

        BookResponseDto response = new BookResponseDto();
        response.setId(updatedBook.getId());
        response.setTitle(updatedBook.getTitle());
        response.setAuthor(updatedBook.getAuthor());
        response.setRating(updatedBook.getRating());
        response.setReview(updatedBook.getReview());

        return response;
    }

    @Override
    public void delete(Long id) {
        // перед видаленням перевіряємо наявність запису в базі, якщо книги немає - викидаємо 404 помилку, якщо є - видаляємо її через репозиторій.
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        repository.delete(book);
    }
}