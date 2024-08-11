package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        var testResult = new TestResult(student);
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        List<Question> questions = questionDao.findAll();
        for (Question question : questions) {
            ioService.printLine(question.text());
            var questionNumber = 1;
            for (Answer answer : question.answers()) {
                ioService.printLine(" " + questionNumber + ". " + answer.text());
                questionNumber++;
            }
            var answerOfStudent = ioService.readIntForRangeWithPrompt(1,
                    (int)question.answers().stream().count(),
                    "What's is your answer?",
                    "");
            testResult.applyAnswer(question, question.answers().get(answerOfStudent - 1).isCorrect());
            ioService.printLine("");
        }

        return testResult;
    }
}