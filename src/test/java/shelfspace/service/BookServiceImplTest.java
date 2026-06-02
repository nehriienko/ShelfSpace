package shelfspace.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import shelfspace.dto.BookRequestDto;
import shelfspace.dto.BookResponseDto;
import shelfspace.entity.Book;
import shelfspace.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnBookResponseDto_WhenDataIsValid() {
        // Given (Початкові дані)
        BookRequestDto requestDto = new BookRequestDto();
        requestDto.setTitle("The Martian Chronicles");
        requestDto.setAuthor("Ray Bradbury");
        requestDto.setRating(9);
        requestDto.setReview("Книга про колонізацію Марса.");

        Book savedBook = new Book();
        savedBook.setId(1L); // Імітуємо, що БД присвоїла ID
        savedBook.setTitle(requestDto.getTitle());
        savedBook.setAuthor(requestDto.getAuthor());
        savedBook.setRating(requestDto.getRating());
        savedBook.setReview(requestDto.getReview());

        // Налаштовуємо Mockito: коли репозиторій викликає save, повернути нашу savedBook
        when(repository.save(any(Book.class))).thenReturn(savedBook);

        // When (Дія, яку тестуємо)
        BookResponseDto result = bookService.create(requestDto);

        // Then (Перевірка результату)
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("The Martian Chronicles", result.getTitle());
        assertEquals(9, result.getRating());

        // Перевіряємо, чи викликався метод save у репозиторія рівно 1 раз
        verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
        // Given (Готуємо неіснуючий ID)
        Long nonExistentId = 999L;

        // Імітуємо, що репозиторій нічого не знайшов (повернув порожній Optional)
        when(repository.findById(nonExistentId)).thenReturn(java.util.Optional.empty());

        // When & Then (Перевіряємо, що викликається саме наша помилка)
        assertThrows(shelfspace.exception.ResourceNotFoundException.class, () -> {
            bookService.getById(nonExistentId);
        });

        // Перевіряємо, що пошук у базі дійсно викликався 1 раз
        verify(repository, times(1)).findById(nonExistentId);
    }

    @Test
    void delete_ShouldCallRepositoryDelete_WhenBookExists() {
        // Given (Початкові дані)
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("The Martian Chronicles");

        // Имитируем, что репозиторий НАШЕЛ книгу по ID (как и делает сервис)
        when(repository.findById(bookId)).thenReturn(java.util.Optional.of(book));

        // When (Действие)
        bookService.delete(bookId);

        // Then (Проверка)
        // Проверяем, что репозиторий вызвал метод delete именно с нашей книгой 1 раз
        verify(repository, times(1)).delete(book);
    }
}