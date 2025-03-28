package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-28T00:57:03+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public AuthorDto toAuthorDto(Author author) {
        if (author == null) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setId(author.getId());
        authorDto.setFullName(author.getFullName());

        return authorDto;
    }

    @Override
    public GenreDto toGenreDto(Genre genre) {
        if (genre == null) {
            return null;
        }

        GenreDto genreDto = new GenreDto();

        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());

        return genreDto;
    }

    @Override
    public BookDto toBookDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(toAuthorDto(book.getAuthor()));
        bookDto.setGenre(toGenreDto(book.getGenre()));

        return bookDto;
    }

    @Override
    public CommentDto toCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setBook(toBookDto(comment.getBook()));

        return commentDto;
    }
}
