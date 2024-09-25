
package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvQuestionDaoTest {

    @Autowired
    private QuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        TestFileNameProvider appProperties = Mockito.mock(AppProperties.class);
        Mockito.when(appProperties.getTestFileName()).thenReturn("questions.csv");
        csvQuestionDao = new CsvQuestionDao(appProperties);
    }

    @Test
    void findAll() {
        List<Question> all = csvQuestionDao.findAll();
        assertEquals(3, all.size());
        Question question = all.get(1);
        assertEquals("How should resources be loaded form jar in Java?", question.text());
        List<Answer> answers = question.answers();
        assertEquals(3, answers.size());
        assertEquals("ClassLoader#geResource#getFile + FileReader", answers.get(1).text());
        assertFalse(answers.get(1).isCorrect());
    }
}
