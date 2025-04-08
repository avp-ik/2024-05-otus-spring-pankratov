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
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.DtoMapperImpl;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.CommentRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тестирование BookController")
@WebFluxTest
@ContextConfiguration(classes = {BookController.class, DtoMapperImpl.class})
class BookControllerTest extends BaseTest {

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

    @DisplayName("Загрузить список всех книг")
    @Test
    void returnAllBooks() {
        doReturn(Flux.fromIterable(dbBooks))
                .when(bookRepository)
                .findAll();

        webTestClient.get()
                .uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(dbBooksDto);

        verify(bookRepository, times(1)).findAll();
    }

    @DisplayName("Загрузить книгу")
    @Test
    void returnBookById() {
        Book expectedBook = dbBooks.get(0);
        BookDto expectedBookDto = dbBooksDto.get(0);
        doReturn(Mono.just(expectedBook)).when(bookRepository).findById(expectedBook.getId());

        webTestClient.get()
                .uri("/api/v1/books/{id}", expectedBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBookDto);

        verify(bookRepository, times((1))).findById(expectedBook.getId());
    }

    @DisplayName("Сохранить новую книгу")
    @Test
    void createBook() throws Exception {
        Book book = new Book(null, "BookTitle_New", dbAuthors.get(1), dbGenres.get(1));

        BookDto bookDto = dtoMapper.toBookDto(book);

        BookCreateDto bookCreateDto = new BookCreateDto(book.getTitle(),
                book.getAuthor().getId().toString(),
                book.getGenre().getId().toString());

        doReturn(Mono.just(book.getAuthor())).when(authorRepository).findById(book.getAuthor().getId());
        doReturn(Mono.just(book.getGenre())).when(genreRepository).findById(book.getGenre().getId());
        doReturn(Mono.just(book)).when(bookRepository).save(any(Book.class));

        webTestClient.post()
                .uri("/api/v1/books")
                .body(BodyInserters.fromValue(bookCreateDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(bookDto);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Сохранить измененную книгу")
    @Test
    void updatedBook() throws Exception {
        Book book = dbBooks.get(2);
        book.setTitle("BookTitle_Change");
        book.setAuthor(dbAuthors.get(0));
        book.setGenre(dbGenres.get(0));

        BookDto bookDto = dtoMapper.toBookDto(book);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(),
                book.getTitle(),
                book.getAuthor().getId().toString(),
                book.getGenre().getId().toString());

        doReturn(Mono.just(book.getAuthor())).when(authorRepository).findById(book.getAuthor().getId());
        doReturn(Mono.just(book.getGenre())).when(genreRepository).findById(book.getGenre().getId());
        doReturn(Mono.just(book)).when(bookRepository).save(any(Book.class));

        webTestClient.put()
                .uri("/api/v1/books")
                .body(BodyInserters.fromValue(bookUpdateDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(bookDto);

        verify(authorRepository, times(1)).findById(book.getAuthor().getId());
        verify(genreRepository, times(1)).findById(book.getGenre().getId());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void deleteBook() throws Exception {
        String bookId = dbBooks.get(2).getId();
        doReturn(Mono.empty()).when(bookRepository).deleteById(bookId);
        doReturn(Mono.empty()).when(commentRepository).deleteAllByBookId(bookId);

        webTestClient.delete()
                .uri("/api/v1/books/{id}", bookId)
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository, times((1))).deleteById(bookId);
        verify(commentRepository, times((1))).deleteAllByBookId(bookId);
    }
}