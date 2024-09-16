package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.QuestionReadException;

@Service
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final LocalizedIOService ioService;

    @Override
    public void run() {
        try {
            var student = studentService.determineCurrentStudent();
            var testResult = testService.executeTestFor(student);
            resultService.showResult(testResult);
        } catch (QuestionReadException e) {
            ioService.printFormattedLineLocalized("TestRunnerServiceImpl.error.of.reading.questions");
        } catch (Throwable e) {
            ioService.printFormattedLineLocalized("TestRunnerServiceImpl.unknown.error");
        }
    }
}