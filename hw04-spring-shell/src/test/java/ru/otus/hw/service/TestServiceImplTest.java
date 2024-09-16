package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceImplTest {

    @Autowired
    private TestService testService;

    @BeforeEach
    void setUp() {
        LocalizedIOService localizedIOServiceImpl = Mockito.mock(LocalizedIOServiceImpl.class);
        Mockito.when(localizedIOServiceImpl.readIntForRangeWithPromptLocalized(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(1);

        List<Question> questionList = List.of(
                new Question("Is there life on Mars?", List.of(
                        new Answer("Science doesn't know this yet", true),
                        new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                        new Answer("Absolutely not", false))),
                new Question("How should resources be loaded form jar in Java?", List.of(
                        new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                        new Answer("ClassLoader#geResource#getFile + FileReader", false),
                        new Answer("Wingardium Leviosa", false))),
                new Question("Which option is a good way to handle the exception?", List.of(
                        new Answer("@SneakyThrow", false),
                        new Answer("e.printStackTrace()", false),
                        new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true),
                        new Answer("Ignoring exception", false))));

        QuestionDao questionDao = Mockito.mock(QuestionDao.class);
        Mockito.when(questionDao.findAll()).thenReturn(questionList);

        testService = new TestServiceImpl(localizedIOServiceImpl, questionDao);
    }

    @Test
    void executeTestFor() {
        Student student = new Student("Ivan", "Ivanov");
        TestResult testResult = testService.executeTestFor(student);
        assertEquals(2, testResult.getRightAnswersCount());
    }
}