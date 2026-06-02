package shelfspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shelfspace.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}