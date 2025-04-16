package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.mappers.DtoMapperImpl;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тестирование CommentController")
@WebFluxTest
@ContextConfiguration(classes = {CommentController.class, DtoMapperImpl.class})
class CommentControllerTest extends BaseTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private BookRepository bookRepository;

    @MockitoBean
    private AuthorRepository authorRepository;

    @MockitoBean
    private GenreRepository genreRepository;

    @MockitoBean
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generateTestData();
    }

    @DisplayName("Загрузить список всех комментариев отдельной книги")
    @Test
    void getAllCommentsByBookId() {
        String bookId = dbBooks.get(0).getId();

        doReturn(Flux.fromIterable(dbComments))
                .when(commentRepository)
                .findAllByBookId(bookId);

        webTestClient.get()
                .uri("/api/v1/books/{id}/comments", bookId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CommentDto.class)
                .isEqualTo(dbCommentsDto);

        verify(commentRepository, times(1)).findAllByBookId(bookId);
    }
}