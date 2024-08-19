package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.Application;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
@DisplayName("Класс TestServiceImpl")
public class TestServiceImplTest {

    private static AppConfig appConfig;
    private static StreamsIOService ioService;
    private static CsvQuestionDao questionDao;

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);
        appConfig = context.getBean(AppConfig.class);
        questionDao = context.getBean(CsvQuestionDao.class);
    }

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {

        TestServiceImpl testServiceImpl = new TestServiceImpl(ioService, questionDao);

        assertTrue(testServiceImpl != null);
    }

    @DisplayName("корректно выполняется успешный тест (3 из 3)")
    @Test
    void executeSuccessTest() {
        TestServiceImplTest.setRightAnswers();

        TestServiceImpl testServiceImpl = new TestServiceImpl(ioService, questionDao);

        TestResult testResult = testServiceImpl.executeTestFor(new Student("Ivan", "Ivanov"));

        assertTrue(testResult.getAnsweredQuestions().size() == testResult.getRightAnswersCount());
    }

    @DisplayName("корректно выполняется неуспешный тест (2 из 3)")
    @Test
    void executeWrongTest() {
        TestServiceImplTest.setWrongAnswers();

        TestServiceImpl testServiceImpl = new TestServiceImpl(ioService, questionDao);

        TestResult testResult = testServiceImpl.executeTestFor(new Student("Ivan", "Ivanov"));

        assertTrue(testResult.getAnsweredQuestions().size() != testResult.getRightAnswersCount());
    }

    static private void setRightAnswers() {
        TestServiceImplTest.ioService = Mockito.mock(StreamsIOService.class);
        Mockito.when(ioService.readIntForRangeWithPrompt(1, 3, "What's is your answer?", ""))
                .thenReturn(1);
        Mockito.when(ioService.readIntForRangeWithPrompt(1, 4, "What's is your answer?", ""))
                .thenReturn(3);
    }

    static private void setWrongAnswers() {
        TestServiceImplTest.ioService = Mockito.mock(StreamsIOService.class);
        Mockito.when(ioService.readIntForRangeWithPrompt(1, 3, "What's is your answer?", ""))
                .thenReturn(1);
        Mockito.when(ioService.readIntForRangeWithPrompt(1, 4, "What's is your answer?", ""))
                .thenReturn(1);
    }

    static private  List<Question> getTestListOfQuestion() {
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
                        new Answer("Ignoring exception", false)))
        );
        return questionList;
    }
}