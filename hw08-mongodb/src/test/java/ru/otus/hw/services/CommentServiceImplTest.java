package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.TestPropertySource;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.converters.GenreConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Тестирование CommentServiceImpl")
@DataMongoTest
@ComponentScan({"ru.otus.hw.services"})
@ComponentScan({"ru.otus.hw.converters"})
@TestPropertySource(locations = "classpath:application.yml")
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentConverter commentConverter;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreConverter genreConverter;

    private String bookIdForTest = "";
    private String commentTextForTest = "Отличная книга!";

    @DisplayName("Проверить сохранение Comment")
    @Test
    void insertCommentsOfBook() {
        var savedBooks = bookService.findAll();
        var savedGenres = genreService.findAll();

        bookIdForTest = savedBooks.get(0).getId();

        var savedComments = commentService.findAllByBookId(bookIdForTest);
        var initialNumberOfComments = savedComments.stream().count();

        var savedComment = commentService.insert(commentTextForTest, bookIdForTest);
        assertEquals(savedComment.getText(), commentTextForTest);
        assertEquals(savedComment.getBook().getId(), bookIdForTest);

        savedComments = commentService.findAllByBookId(bookIdForTest);
        assertEquals(savedComments.stream().count(), initialNumberOfComments + 1);
    }

    @DisplayName("Получить Comment по bookId или genreId")
    @Test
    void getCommentsOfBookOrGenre() {
        commentTextForTest = "Отличная книга!";

        var savedComments = commentService.findAllByCommentText(commentTextForTest);
        assertNotEquals(savedComments.stream().count(), 0);
    }
}