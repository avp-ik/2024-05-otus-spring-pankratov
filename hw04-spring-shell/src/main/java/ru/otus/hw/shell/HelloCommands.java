package ru.otus.hw.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HelloCommands {

    @ShellMethod(value = "I will say hello", key = "hello")
    public String hello() {
        return "Hello, world!";
    }

    @ShellMethod(value = "I will say goodbye", key = "goodbye")
    public String goodbye() {
        return "Goodbye!";
    }

}
