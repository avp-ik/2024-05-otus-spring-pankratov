package ru.otus.hw.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.hw.repositories.BookRepository;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class GetCorrectBookHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        SecureRandom secureRandom = new SecureRandom();

        Long bookId = secureRandom.nextLong(bookRepository.count() * 2) + 1;

        if (bookRepository.findById(bookId).isPresent()) {
            return Health.up().status(Status.UP)
                    .withDetail("message", "ОК!").build();
        } else {
            return Health.down().status(Status.DOWN)
                    .withDetail("message", "Не существует книги по запрошенному идентификатору!").build();
        }
    }
}
