package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.CommentConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Тестирование CommentServiceImpl")
@SpringBootTest
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

    private final Long BOOK_ID_FOR_TEST = 1L;
    private final Long GENRE_ID_FOR_TEST = 1L;
    private final String COMMENT_TEXT_FOR_TEST = "Some comment.";

    @DisplayName("Проверить сохранение Comment")
    @Test
    void insertCommentsOfBook() {
        var savedComments = commentService.findAllByBookId(BOOK_ID_FOR_TEST);
        var initialNumberOfComments = savedComments.stream().count();

        var savedComment = commentService.insert(COMMENT_TEXT_FOR_TEST, BOOK_ID_FOR_TEST);
        assertEquals(savedComment.getText(), COMMENT_TEXT_FOR_TEST);
        assertEquals(savedComment.getBook().getId(), BOOK_ID_FOR_TEST);

        savedComments = commentService.findAllByBookId(BOOK_ID_FOR_TEST);
        assertEquals(savedComments.stream().count(), initialNumberOfComments + 1);
    }

    @DisplayName("Получить Comment по bookId или genreId")
    @Test
    void getCommentsOfBookOrGenre() {
        var savedComments = commentService.findAllByBookIdOrGenreId(BOOK_ID_FOR_TEST, GENRE_ID_FOR_TEST);

        assertNotEquals(savedComments.stream().count(), 0);
    }
}