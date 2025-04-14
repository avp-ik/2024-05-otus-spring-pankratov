package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.DtoMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    private final DtoMapper dtoMapper;

    @GetMapping("/api/v1/books")
    public Flux<BookDto> listBooks() {
        return bookRepository.findAll().map(dtoMapper::toBookDto);
    }

    @GetMapping("/api/v1/books/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(dtoMapper::toBookDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %s not found".formatted(id))));
    }

    @PostMapping("/api/v1/books")
    public Mono<BookDto> addBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        return add(bookCreateDto.getTitle(),
                bookCreateDto.getAuthorId(),
                bookCreateDto.getGenreId());
    }

    @PutMapping("/api/v1/books")
    public Mono<BookDto> updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return update(bookUpdateDto.getId(),
                bookUpdateDto.getTitle(),
                bookUpdateDto.getAuthorId(),
                bookUpdateDto.getGenreId());
    }

    @DeleteMapping("/api/v1/books/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id).then(commentRepository.deleteAllByBookId(id));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleException1() {
    }

    private Mono<BookDto> add(String title, String authorId, String genreId) {
        return authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Author with id %s not found"
                        .formatted(authorId))))
                .zipWith(genreRepository.findById(genreId))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Genre with id %s not found".formatted(genreId))))
                .flatMap(tuple2 -> bookRepository.insert(new Book(null, title, tuple2.getT1(), tuple2.getT2()))
                        .map(dtoMapper::toBookDto));
    }

    private Mono<BookDto> update(String id, String title, String authorId, String genreId) {
        return authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Author with id %s not found"
                        .formatted(authorId))))
                .zipWith(genreRepository.findById(genreId))
                        .switchIfEmpty(Mono.error(new EntityNotFoundException("Genre with id %s not found"
                                .formatted(genreId))))
                .flatMap(tuple2 -> (bookRepository.findById(id)
                                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %s not found"
                                        .formatted(id))))
                                .doOnNext(book -> {
                                    book.setTitle(title);
                                    book.setAuthor(tuple2.getT1());
                                    book.setGenre(tuple2.getT2());
                                })
                                .flatMap(bookRepository::save).map(dtoMapper::toBookDto)
                        )
                );
    }
}