package shelfspace.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BookRequestDto {

    @NotBlank(message = "Title is required") // @NotBlank перевіряє, що рядок не є порожнім, message задає кастомний текст помилки, який повернеться клієнту у разі порушення правила.
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    // @Min та @Max встановлюють числові межі для поля. обмежують оцінку книги від 1 до 10
    @Min(value = 1, message = "Rating must be between 1 and 10")
    @Max(value = 10, message = "Rating must be between 1 and 10")
    private Integer rating;

    private String review;


    public BookRequestDto() { //конструктор без параметрів. обов'язковий для бібліотеки Jackson, яка автоматично конвертує JSON-текст у цей Java-об'єкт.
    }

    // Геттери та сеттери, щоб Spring та Jackson  зчитували та записували дані у приватні поля.
    public String getTitle() {
        // Повертає назву книги
        return title;
    }

    public void setTitle(String title) {
        // Встановлює назву книги
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}