package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.otus.hw.converters.BookConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестирование CommentServiceImpl")
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    private final String BOOK_TITLE_FOR_TEST = "Some book";
    private final Long AUTHOR_ID_FOR_TEST = 1L;
    private final Long GENRE_ID_FOR_TEST = 1L;

    @DisplayName("Проверить сохранение Book")
    @Test
    void countBook() {
        var savedBooks = bookService.findAll();
        var initialNumberOfBooks = savedBooks.stream().count();

        var savedBook = bookService.create(BOOK_TITLE_FOR_TEST, AUTHOR_ID_FOR_TEST, GENRE_ID_FOR_TEST);
        assertEquals(savedBook.getTitle(), BOOK_TITLE_FOR_TEST);
        assertEquals(savedBook.getAuthor().getId(), AUTHOR_ID_FOR_TEST);
        assertEquals(savedBook.getGenre().getId(), GENRE_ID_FOR_TEST);

        savedBooks = bookService.findAll();
        assertEquals(savedBooks.stream().count(), initialNumberOfBooks + 1);
    }
}