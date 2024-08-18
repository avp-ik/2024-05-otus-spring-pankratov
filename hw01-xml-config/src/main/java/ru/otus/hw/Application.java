package ru.otus.hw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {

        System.out.println("Hello, Spring Framework HW01!");

        ApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
                    var testRunnerService = context.getBean(TestRunnerService.class);
                    testRunnerService.run();

    }
}