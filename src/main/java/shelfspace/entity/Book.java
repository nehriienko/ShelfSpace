package shelfspace.entity;

import jakarta.persistence.*;

@Entity
// явно задає ім'я таблиці в реляційній базі даних , до якої буде прив'язаний цей клас
@Table(name = "books")
public class Book {

    @Id  // позначає це поле як первинний ключ для цієї таблиці.
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // @GeneratedValue визначає стратегію автоматичної генерації значень первинного ключа.
    private Long id;

    private String title;

    private String author;

    private Integer rating;

    private String review;

    public Book() {
    }

    // геттери та сеттери, потрібні JPA для доступу до приватних полів сутності та управління їхнім станом .
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