package ru.otus.hw.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class BookRequestCounter {
//https://dev.to/wn/monitoring-your-spring-boot-application-with-custom-metrics-and-prometheus-36h9

    private final Counter bookRequestCounter;

    public BookRequestCounter(MeterRegistry registry) {
        bookRequestCounter = Counter
                .builder("book_request_counter")
                .description("This counter count number of requests of books through API.")
                .register(registry);
    }

    public double increment() {
        bookRequestCounter.increment();
        return bookRequestCounter.count();
    }

    public double increment(double number) {
        bookRequestCounter.increment(number);
        return bookRequestCounter.count();
    }
}