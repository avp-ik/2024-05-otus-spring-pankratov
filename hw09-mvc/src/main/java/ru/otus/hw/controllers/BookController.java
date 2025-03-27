package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @RequestMapping("/hello")
    public String hello(
            @RequestParam String name,
            Model model
    ) {
        model.addAttribute(
                "username", name
        );
        return "helloPage";
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "list";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam("id") long id, Model model) {
        BookDto book;
        if (id == 0) {
            book = new BookDto();
            book.setId(id);
        } else {
            book = bookService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        }
        fillModelAttributes(model, book);

        var returnValue = "update";
        if (id == 0) {
            returnValue = "create";
        }
        return returnValue;
    }

    @PostMapping("/book/create")
    public String createBook(@Valid @ModelAttribute("book") BookDto book,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(log::warn);

            fillModelAttributes(model, book);

            return "create";
        }

        bookService.create(book);

        return "redirect:/";
    }

    @PutMapping("/book/update")
    public String updateBook(@Valid @ModelAttribute("book") BookDto book,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(log::warn);

            fillModelAttributes(model, book);

            return "update";
        }

        bookService.update(book);

        return "redirect:/";
    }

    @DeleteMapping("/book/delete")
    public String deleteBook (@RequestParam("id") long id) {
        bookService.deleteById(id);

        return "redirect:/";
    }

    private void fillModelAttributes(Model model, BookDto book) {
        model.addAttribute("book", book);
        model.addAttribute("authorList", authorService.findAll());
        model.addAttribute("genreList", genreService.findAll());
    }
}