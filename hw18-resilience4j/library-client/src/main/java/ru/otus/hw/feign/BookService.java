package ru.otus.hw.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;

import java.util.List;

@FeignClient(name = "book-service",
        url = "${library-service.url}",
        fallback = BookServiceImpl.class)
public interface BookService {

    @GetMapping("/api/v1/books")
    public List<BookDto> findAll();

    @GetMapping("/api/v1/books/{id}")
    public BookDto findById(@PathVariable("id") long id);

    @PostMapping("/api/v1/books")
    public BookDto create(@RequestBody @Valid BookCreateDto bookCreateDto);

    @PutMapping("/api/v1/books")
    public BookDto update(@RequestBody @Valid BookUpdateDto bookUpdateDto);

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteById(@PathVariable("id") long id);

}