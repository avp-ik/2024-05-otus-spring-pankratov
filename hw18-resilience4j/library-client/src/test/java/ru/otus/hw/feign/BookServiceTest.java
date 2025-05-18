package ru.otus.hw.feign;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.dto.BookDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Интеграционный тест BookController")
public class BookServiceTest {

    @Autowired
    protected CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    protected BookController bookController;

    public void openAllCircuitBreaker() {
        Set<CircuitBreaker> circuitBreakers = circuitBreakerRegistry.getAllCircuitBreakers();
        for (CircuitBreaker circuitBreaker : circuitBreakers)
        {
            circuitBreaker.transitionToOpenState();
        }
    }

    @Test
    @DisplayName("Получить книги при Circuit Breaker Status = Open")
    public void getListBooksTest() {

        //circuitBreakerRegistry.circuitBreaker("book-service").transitionToOpenState();

        List<BookDto> books = bookController.listBooks();

        //assertNotEquals(3, books.size());

        openAllCircuitBreaker();

        books = bookController.listBooks();

        assertEquals(List.of(new BookDto(0, "", null, null)), books);
    }
}
