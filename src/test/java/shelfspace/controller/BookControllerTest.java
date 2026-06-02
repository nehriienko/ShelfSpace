package shelfspace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shelfspace.dto.BookRequestDto;
import shelfspace.service.BookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService; // Ізолюємо контролер від реального сервісу

    @Autowired
    private ObjectMapper objectMapper; // Допомагає перетворити об'єкт Java в JSON текст

    @Test
    void create_ShouldReturnBadRequest_WhenRatingIsInvalid() throws Exception {
        // Given (Готуємо некоректне DTO з рейтингом 15)
        BookRequestDto invalidDto = new BookRequestDto();
        invalidDto.setTitle("Некоректна Книга");
        invalidDto.setAuthor("Автор");
        invalidDto.setRating(15); // Помилка тут! Максимум має бути 10
        invalidDto.setReview("Якийсь відгук");

        // When & Then (Імітуємо POST запит і перевіряємо, що сервер відмовить)
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest()) // Перевіряємо, що статус 400 Bad Request
                .andExpect(jsonPath("$.rating").value("Rating must be between 1 and 10"));
        // Перевіряємо, що в JSON відповіді є наше повідомлення про помилку
    }
}