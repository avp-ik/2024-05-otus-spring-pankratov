package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.converters.BookConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестирование BookServiceImpl")
@DataMongoTest
@ComponentScan({"ru.otus.hw.services"})
@ComponentScan({"ru.otus.hw.converters"})
@TestPropertySource(locations = "classpath:application.yml")
@ExtendWith(SpringExtension.class)
public class BookServiceImplTest {
    @DisplayName("Выполнить пробный тест")
    @Test
    void BaseTest() {
        assertEquals(true, true);
    }

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookConverter bookConverter;

    private String bookTitleForTest = "Some book";
    private String authorIdForTest = "";
    private String genreIdForTest = "";

    @DisplayName("Проверить сохранение Book")
    @Test
    void countBook() {
        var savedBooks = bookService.findAll();
        var savedGenres = genreService.findAll();
        var savedAuthors = authorService.findAll();

        authorIdForTest = savedAuthors.get(0).getId();
        genreIdForTest = savedGenres.get(0).getId();

        var initialNumberOfBooks = savedBooks.stream().count();

        var savedBook = bookService.create(bookTitleForTest, authorIdForTest, genreIdForTest);
        assertEquals(savedBook.getTitle(), bookTitleForTest);
        assertEquals(savedBook.getAuthor().getId(), authorIdForTest);
        assertEquals(savedBook.getGenre().getId(), genreIdForTest);

        savedBooks = bookService.findAll();
        assertEquals(savedBooks.stream().count(), initialNumberOfBooks + 1);
    }
}