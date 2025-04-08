package ru.otus.hw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.DtoMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseTest {

    @Autowired
    protected DtoMapper dtoMapper;

    protected final List<Author> dbAuthors = new ArrayList<>();
    protected final List<AuthorDto> dbAuthorsDto = new ArrayList<>();

    protected final List<Genre> dbGenres = new ArrayList<>();
    protected final List<GenreDto> dbGenresDto = new ArrayList<>();

    protected final List<Book> dbBooks = new ArrayList<>();
    protected final List<BookDto> dbBooksDto = new ArrayList<>();

    protected final List<Comment> dbComments = new ArrayList<>();
    protected final List<CommentDto> dbCommentsDto = new ArrayList<>();

    protected void generateTestData() {
        for (int i = 1; i < 10; i++) {
            Author author = new Author(UUID.randomUUID().toString(), "Author_%s".formatted(i));
            Genre genre = new Genre(UUID.randomUUID().toString(), "Genre_%s".formatted(i));
            Book book = new Book(UUID.randomUUID().toString(), "BookTitle_%s".formatted(i), author, genre);

            dbAuthors.add(author);
            dbAuthorsDto.add(dtoMapper.toAuthorDto(author));
            dbGenres.add(genre);
            dbGenresDto.add(dtoMapper.toGenreDto(genre));
            dbBooks.add(book);
            dbBooksDto.add(dtoMapper.toBookDto(book));

            for (int j = 1; j < 10; j++) {
                Comment comment = new Comment(UUID.randomUUID().toString(), "Comment_%s".formatted(j), book);
                dbComments.add(comment);
                dbCommentsDto.add(dtoMapper.toCommentDto(comment));
            }
        }
    }
}
