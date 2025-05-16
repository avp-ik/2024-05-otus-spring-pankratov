package ru.otus.hw.runners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Egg;
import ru.otus.hw.service.IncubatorService;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {
    private final IncubatorService service;

    @Override
    public void run(String... args) {
        var eggs = List.of(
                new Egg("Red admiral"),
                new Egg("Small tortoiseshell"),
                new Egg("Peacock butterfly"),
                new Egg("Morpho"),
                new Egg("Burnet moth"),
                new Egg("Painted lady"),
                new Egg("Swallowtail butterfly")
        );
        service.runProcess(eggs);
    }
}
