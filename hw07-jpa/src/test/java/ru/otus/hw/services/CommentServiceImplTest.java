package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестирование CommentServiceImpl")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
//@Import({CommentServiceImplTest.class})
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;
//
//    @Autowired
//    private CommentConverter commentConverter;
//
//    @Autowired
//    private BookService bookService;
//
//    @Autowired
//    private BookConverter bookConverter;

    @BeforeEach
    void setUp() {
        String text = "SomeBooks";
        Long bookId = 10L;

        //var savedComment = commentService.insert(text, bookId);
        //assertEquals(savedComment.getText(), text);
        //assertEquals(savedComment.getBook().getId(), bookId);
        //assertEquals(2, 2);
    }

    @Test
    void countCommentsOfBook() {
        Long bookId = 10L;

        var savedComments = commentService.findAllByBookId(bookId);
        assertEquals(savedComments.stream().count(), 1);
        //assertEquals(2, 2);
    }
}
