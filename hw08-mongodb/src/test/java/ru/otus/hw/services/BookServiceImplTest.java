package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.converters.BookConverter;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование BookServiceImpl")
@DataMongoTest
@ComponentScan({"ru.otus.hw.services"})
@ComponentScan({"ru.otus.hw.converters"})
@ComponentScan({"ru.otus.hw.listeners"})
@ExtendWith(SpringExtension.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookConverter bookConverter;

    @DisplayName("Проверить создание Book")
    @Test
    void createBook() {
        var savedBooks = bookService.findAll();
        var savedGenres = genreService.findAll();
        var savedAuthors = authorService.findAll();

        var bookTitleForTest = "Some book";
        var authorIdForTest = savedAuthors.get(0).getId();
        var genreIdForTest = savedGenres.get(0).getId();

        var initialNumberOfBooks = savedBooks.stream().count();

        var savedBook = bookService.create(bookTitleForTest, authorIdForTest, genreIdForTest);
        assertEquals(savedBook.getTitle(), bookTitleForTest);
        assertEquals(savedBook.getAuthor().getId(), authorIdForTest);
        assertEquals(savedBook.getGenre().getId(), genreIdForTest);

        savedBooks = bookService.findAll();
        assertEquals(savedBooks.stream().count(), initialNumberOfBooks + 1);
    }

    @DisplayName("Проверить обновление Book")
    @Test
    void updateBook() {
        var savedBooks = bookService.findAll();
        var savedGenres = genreService.findAll();
        var savedAuthors = authorService.findAll();

        var bookTitleForTest = "Changed title";
        var authorIdForTest = savedAuthors.get(0).getId();
        var genreIdForTest = savedGenres.get(0).getId();

        var initialNumberOfBooks = savedBooks.stream().count();

        var savedBook = bookService.update(savedBooks.get(0).getId(), bookTitleForTest, authorIdForTest, genreIdForTest);

        assertEquals(savedBook.getTitle(), bookTitleForTest);
        assertEquals(savedBook.getAuthor().getId(), authorIdForTest);
        assertEquals(savedBook.getGenre().getId(), genreIdForTest);

        savedBooks = bookService.findAll();
        assertEquals(savedBooks.stream().count(), initialNumberOfBooks);
    }

    @DisplayName("Получить все Book")
    @Test
    void findAllBook() {
        var savedBooks = bookService.findAll();

        var initialNumberOfBooks = savedBooks.stream().count();

        assertEquals(initialNumberOfBooks > 0, true);
    }

    @DisplayName("Получить по ID Book")
    @Test
    void findByIdBook() {
        var savedBooks = bookService.findAll();

        var bookId = savedBooks.get(0).getId();

        var book = bookService.findById(bookId);

        assertNotNull(book);
    }

    @DisplayName("Удаление Book")
    @Test
    void deleteBook() {
        var savedBooks = bookService.findAll();
        var bookId = savedBooks.get(0).getId();

        var linkedComments = commentService.findAllByBookId(bookId);

        assertTrue(linkedComments.size() > 0);

        bookService.deleteById(bookId);

        linkedComments = commentService.findAllByBookId(bookId);

        assertTrue(linkedComments.size() == 0);
    }
}