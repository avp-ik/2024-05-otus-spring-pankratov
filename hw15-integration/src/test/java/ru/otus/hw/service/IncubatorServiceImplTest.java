package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.models.Butterfly;
import ru.otus.hw.models.Egg;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class IncubatorServiceImplTest {

    @Autowired
    private IncubatorService incubatorService;

    private Collection<Egg> eggs;

    @BeforeEach
    void setUp() {
        generateTestData();
    }

    private void generateTestData() {
        this.eggs = List.of(
                new Egg("Red admiral"),
                new Egg("Small tortoiseshell"),
                new Egg("Peacock butterfly"),
                new Egg("Morpho"),
                new Egg("Burnet moth"),
                new Egg("Painted lady"),
                new Egg("Swallowtail butterfly")
        );
    }

    @Test
    @DisplayName("Выполнить интеграционный процесс превращения яиц в бабочек")
    void doProcess() throws Exception {

        Collection<Butterfly> butterflies = incubatorService.runProcess(this.eggs);

        assertThat(butterflies.size()).isEqualTo(eggs.size());
    }
}
