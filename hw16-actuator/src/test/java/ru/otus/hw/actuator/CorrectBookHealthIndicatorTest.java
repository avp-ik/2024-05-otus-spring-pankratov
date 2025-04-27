package ru.otus.hw.actuator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


@SpringBootTest(classes = {CorrectBookHealthIndicator.class})
@DisplayName("Тестирование CorrectBookHealthIndicator")
class CorrectBookHealthIndicatorTest {

    @Autowired
    private CorrectBookHealthIndicator correctBookHealthIndicator;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void doCheckHealthTest() {
        Book book = new Book(0,
                "Круть",
                new Author(1L, "Пелевин Виктор"),
                new Genre(1L, "Фантастика"));

        Optional<Book> optionalBook = Optional.of(book);
        // Если count вернет 1, то в getCorrectBookHealthIndicator.health будет попытка найти Book по Id = 0 или Id = 1.
        doReturn(1L).when(bookRepository).count();
        // findById должен вернуть объект в обоих случаях (Id = 0 или Id = 1).
        doReturn(optionalBook).when(bookRepository).findById(0L);
        doReturn(optionalBook).when(bookRepository).findById(1L);

        Health health = correctBookHealthIndicator.health();
        assertEquals(Status.UP, health.getStatus());
    }
}