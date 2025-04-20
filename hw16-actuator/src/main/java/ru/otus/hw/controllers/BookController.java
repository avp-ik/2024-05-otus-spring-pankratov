package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.actuator.BookRequestCounter;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    private final BookRequestCounter bookRequestCounter;

    @GetMapping()
    public List<BookDto> listBooks() {
        List<BookDto> bookDtoList = bookService.findAll();
        bookRequestCounter.increment(bookDtoList.size());
        return bookDtoList;
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        BookDto bookDto = bookService.findById(id);
        bookRequestCounter.increment();
        return bookDto;
    }

    @PostMapping()
    public BookDto createBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @PutMapping()
    public BookDto updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return bookService.update(bookUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook (@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}