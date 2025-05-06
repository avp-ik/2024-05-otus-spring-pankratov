package ru.otus.hw.runners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.IncubatorService;


@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {
    private final IncubatorService service;

    @Override
    public void run(String... args) {
        service.runProcess();
    }
}
