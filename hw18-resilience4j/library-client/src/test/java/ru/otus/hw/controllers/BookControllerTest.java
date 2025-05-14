package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import ru.otus.hw.actuator.BookRequestCounter;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.feign.AuthorService;
import ru.otus.hw.feign.BookService;
import ru.otus.hw.feign.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование BookController")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookRequestCounter bookRequestCounter;

    private List<AuthorDto> dbAuthors = new ArrayList<>();

    private List<GenreDto> dbGenres = new ArrayList<>();

    private List<BookDto> dbBooks = new ArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        generateTestData();
    }

    @DisplayName("Сохранить новую книгу")
    @Test
    void createBook() throws Exception {
        var title = "Война и мир";
        var author = dbAuthors.get(1);
        var genre = dbGenres.get(1);

        BookCreateDto bookCreateDto = new BookCreateDto(title,
                author.getId(),
                genre.getId());

        BookDto bookDto = new BookDto(100,
                title,
                author,
                genre);

        doReturn(bookDto).when(bookService).create(bookCreateDto);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bookDto)));

        verify(bookService, times(1)).create(bookCreateDto);
    }

    @DisplayName("Сохранить измененную книгу")
    @Test
    void updatedBook() throws Exception {
        var title = "Детство. Отрочество. Юность.";
        var bookDto = dbBooks.get(2);
        var author = dbAuthors.get(1);
        var genre = dbGenres.get(1);

        BookUpdateDto bookUpdateDto = new BookUpdateDto(bookDto.getId(),
                title,
                author.getId(),
                genre.getId());

        doReturn(bookDto).when(bookService).update(bookUpdateDto);

        mockMvc.perform(put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bookDto)));

        verify(bookService, times(1)).update(bookUpdateDto);
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void deleteBook() throws Exception {

        mockMvc.perform(delete("/api/v1/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteById(1L);
    }

    private void generateTestData() {
        for (int i = 1; i < 10; i++) {
            AuthorDto author = new AuthorDto(i, "Author_%s".formatted(i));
            GenreDto genre = new GenreDto(i, "Genre_%s".formatted(i));
            BookDto book = new BookDto(i, "BookTitle_%s".formatted(i), author, genre);

            dbAuthors.add(author);
            dbGenres.add(genre);
            dbBooks.add(book);
        }
    }
}