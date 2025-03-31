package ru.otus.hw.services;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(String title, long authorId, long genreId);

    BookDto create(BookDto bookDto);

    BookDto create(BookCreateDto bookCreateDto);

    BookDto update(long id, String title, long authorId, long genreId);

    BookDto update(BookDto book);

    BookDto update(BookUpdateDto bookUpdateDto);

    void deleteById(long id);
}
