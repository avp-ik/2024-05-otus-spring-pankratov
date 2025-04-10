package ru.otus.hw.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.configurations.SecurityConfiguration;
import ru.otus.hw.dto.*;
import ru.otus.hw.mappers.DtoMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.services.UserDetailsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование BookController")
@WithMockUser(username = "TestUser")
@WebMvcTest({BookController.class, SecurityConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

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

    @DisplayName("Загрузить список всех книг")
    @Test
    @Order(1)
    void getAllBooks() throws Exception {
        doReturn(dbBooks).when(bookService).findAll();

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookService, times((1))).findAll();
    }

    @DisplayName("Unauth: Нет доступа к списку книг")
    @Test
    @WithAnonymousUser
    @Order(2)
    void doNotGetAllBooks() throws Exception {
        doReturn(dbBooks).when(bookService).findAll();

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Получить книгу")
    @Test
    @Order(3)
    void getBook() throws Exception {
        BookDto expectedBook = dbBooks.get(0);
        doReturn(expectedBook).when(bookService).findById(expectedBook.getId());

        mockMvc.perform(get("/book").param("id", Long.toString(expectedBook.getId())))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookService, times(1)).findById(expectedBook.getId());
    }

    @DisplayName("Unauth: Нет доступа к книге")
    @Test
    @WithAnonymousUser
    @Order(4)
    void doNotGetBook() throws Exception {
        BookDto expectedBook = dbBooks.get(0);
        doReturn(expectedBook).when(bookService).findById(expectedBook.getId());

        mockMvc.perform(get("/book").param("id", Long.toString(expectedBook.getId())))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Сохранить новую книгу")
    @Test
    @Order(5)
    void createBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto(1L,"Война и мир", dbAuthors.get(1).getId(), dbGenres.get(1).getId());

        mockMvc.perform(post("/book/create").flashAttr("bookCreateDto", bookCreateDto))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService, times(1)).create(bookCreateDto);
    }

    @DisplayName("Unauth: Нет возможности создать книгу")
    @Test
    @WithAnonymousUser
    @Order(6)
    void doNotCreateBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto(1L,"Война и мир", dbAuthors.get(1).getId(), dbGenres.get(1).getId());

        mockMvc.perform(post("/book/create").flashAttr("bookCreateDto", bookCreateDto))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Сохранить измененную книгу")
    @Test
    @Order(7)
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

    @DisplayName("Unauth: Нет возможности изменить книгу")
    @Test
    @WithAnonymousUser
    @Order(8)
    void doNotUpdatedBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(dbBooks.get(2).getId());
        bookUpdateDto.setTitle("Детство. Отрочество. Юность.");
        bookUpdateDto.setAuthorId(dbAuthors.get(0).getId());
        bookUpdateDto.setGenreId(dbGenres.get(0).getId());

        mockMvc.perform(put("/book/update").flashAttr("bookUpdateDto", bookUpdateDto))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Удалить книгу по id")
    @Test
    @Order(9)
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/book/delete").param("id", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService, times(1)).deleteById(2L);
    }

    @DisplayName("Unauth: Нет возможности удалить книгу")
    @Test
    @WithAnonymousUser
    @Order(10)
    void doNotDeleteBook() throws Exception {
        mockMvc.perform(delete("/book/delete").param("id", "2"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
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