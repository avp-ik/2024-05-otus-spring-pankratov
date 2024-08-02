package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс TestServiceImpl")
public class TestServiceImplTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        TestServiceImpl testServiceImpl = new TestServiceImpl(null, null);
        assertTrue(testServiceImpl != null);
    }
}