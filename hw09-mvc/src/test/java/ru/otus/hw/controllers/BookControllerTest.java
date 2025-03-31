package ru.otus.hw.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.*;
import ru.otus.hw.mappers.DtoMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
    private DtoMapper dtoMapper;

    private List<AuthorDto> dbAuthors = new ArrayList<>();

    private List<GenreDto> dbGenres = new ArrayList<>();

    private List<BookDto> dbBooks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        generateTestData();
    }

    @DisplayName("Сохранить новую книгу")
    @Test
    void createBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto(0,"Война и мир", dbAuthors.get(1).getId(), dbGenres.get(1).getId());

        mockMvc.perform(post("/book/create").flashAttr("bookCreateDto", bookCreateDto))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService, times(1)).create(bookCreateDto);
    }

    @DisplayName("Сохранить измененную книгу")
    @Test
    void updatedBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(dbBooks.get(2).getId());
        bookUpdateDto.setTitle("Детство. Отрочество. Юность.");
        bookUpdateDto.setAuthorId(dbAuthors.get(0).getId());
        bookUpdateDto.setGenreId(dbGenres.get(0).getId());

        mockMvc.perform(put("/book/update").flashAttr("bookUpdateDto", bookUpdateDto))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService, times(1)).update(bookUpdateDto);
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/book/delete").param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
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