package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.converters.GenreConverter;

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

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreConverter genreConverter;

    @DisplayName("Проверить сохранение Comment")
    @Test
    void insertCommentsOfBook() {
        var savedBooks = bookService.findAll();

        var bookIdForTest = savedBooks.get(0).getId();
        var commentTextForTest = "Отличная книга!";

        var savedComments = commentService.findAllByBookId(bookIdForTest);
        var initialNumberOfComments = savedComments.stream().count();

        var savedComment = commentService.insert(commentTextForTest, bookIdForTest);
        assertEquals(savedComment.getText(), commentTextForTest);
        assertEquals(savedComment.getBook().getId(), bookIdForTest);

        savedComments = commentService.findAllByBookId(bookIdForTest);
        assertEquals(savedComments.stream().count(), initialNumberOfComments + 1);
    }

//    @DisplayName("Получить Comment по commentText")
//    @Test
//    void getCommentsOfBookOrGenre() {
//        var commentTextForTest = "Отличная книга!";
//
//        var savedComments = commentService.findAllByCommentText(commentTextForTest);
//        assertEquals(savedComments.stream().count(), 2);
//    }
}