package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс TestServiceImpl")
public class TestServiceImplTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        PrintStream printStream = System.out;
        StreamsIOService ioService = new StreamsIOService(printStream);

        CsvQuestionDao questionDao = new CsvQuestionDao(new AppProperties("questionsTest.csv"));

        TestServiceImpl testServiceImpl = new TestServiceImpl(ioService, questionDao);

        assertTrue(ioService != null);
        assertTrue(questionDao != null);
        assertTrue(testServiceImpl != null);
    }
}