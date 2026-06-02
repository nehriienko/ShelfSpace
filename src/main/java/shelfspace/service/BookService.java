package shelfspace.service;

import shelfspace.dto.BookRequestDto;
import shelfspace.dto.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto create(BookRequestDto dto); // приймає дані для створення (Request DTO), повертає сформований результат із ID (Response DTO).

    List<BookResponseDto> getAll(); // повертає список усіх наявних книг, які автоматично загорнуті у Response DTO.

    BookResponseDto getById(Long id); // шукає конкретну книгу за її унікальним ідентифікатором

    BookResponseDto update(Long id, BookRequestDto dto); // оновлює існуючу книгу за її ID новими даними, які прийшли у Request DTO.

    void delete(Long id); // видаляє книгу за її унікальним ідентифікатором без повернення даних
}