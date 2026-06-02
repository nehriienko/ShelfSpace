package shelfspace.dto;

public class BookResponseDto {
    // ідентифікатор книги з бази даних потрібен, щоб потім мати змогу оновити (PUT) або видалити (DELETE) конкретну книгу.

    private Long id;

    private String title;

    private String author;

    private Integer rating;

    private String review;

    public BookResponseDto() { // порожній конструктор без параметрів, для Jackson , щоб вона могла
        // автоматично створювати екземпляр класу під час серіалізації об'єкта в JSON-рядок
    }
    // геттери та сеттери, потрібні Spring Boot, щоб витягувати значення з полей об'єкту і формувати з них  JSON-боді відповіді клієнту.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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