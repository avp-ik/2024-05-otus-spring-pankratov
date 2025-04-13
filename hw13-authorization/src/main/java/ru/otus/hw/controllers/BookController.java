package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.DtoMapper;
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

    private final DtoMapper dtoMapper;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> bookDtoList = bookService.findAll();
        model.addAttribute("bookList", bookDtoList);

        return "list";
    }

    @GetMapping("/book")
    public String getBook(@RequestParam("id") long id, Model model) {
        if (id == 0) {
            BookCreateDto bookCreateDto = new BookCreateDto();
            bookCreateDto.setId(id);
            fillModelAttributes(model, bookCreateDto);
        } else {
            BookDto bookDto = bookService.findById(id);
            BookUpdateDto bookUpdateDto = new BookUpdateDto(
                    bookDto.getId(),
                    bookDto.getTitle(),
                    bookDto.getAuthor().getId(),
                    bookDto.getGenre().getId()
            );
            fillModelAttributes(model, bookUpdateDto);
        }

        return (id == 0) ? "create" : "update";
    }

    @PostMapping("/book/create")
    public String createBook(@Valid @ModelAttribute("bookCreateDto") BookCreateDto bookCreateDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(log::warn);

            fillModelAttributes(model, bookCreateDto);

            return "create";
        }

        bookService.create(bookCreateDto);

        return "redirect:/";
    }

    @PutMapping("/book/update")
    public String updateBook(@Valid @ModelAttribute("bookUpdateDto") BookUpdateDto bookUpdateDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(log::warn);

            fillModelAttributes(model, bookUpdateDto);

            return "update";
        }

        bookService.update(bookUpdateDto);

        return "redirect:/";
    }

    @DeleteMapping("/book/delete")
    public String deleteBook (@RequestParam("id") long id) {
        bookService.deleteById(id);

        return "redirect:/";
    }

    private void fillModelAttributes(Model model, BookCreateDto bookCreateDto) {
        model.addAttribute("bookCreateDto", bookCreateDto);
        model.addAttribute("authorList", authorService.findAll());
        model.addAttribute("genreList", genreService.findAll());
    }

    private void fillModelAttributes(Model model, BookUpdateDto bookUpdateDto) {
        model.addAttribute("bookUpdateDto", bookUpdateDto);
        model.addAttribute("authorList", authorService.findAll());
        model.addAttribute("genreList", genreService.findAll());
    }
}