package ru.otus.hw.feign;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    @Override
    public List<BookDto> findAll() {
        return List.of(new BookDto(0L, "", null, null));
    }

    @Override
    public BookDto findById(long id) {
        return new BookDto(0L, "", null, null);
    }

    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        return new BookDto(0L, "", null, null);
    }

    @Override
    public BookDto update(BookUpdateDto bookUpdateDto) {
        return new BookDto(0L, "", null, null);
    }

    @Override
    public void deleteById(long id) {

    }
}