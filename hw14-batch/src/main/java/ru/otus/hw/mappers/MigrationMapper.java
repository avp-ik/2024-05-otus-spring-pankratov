package ru.otus.hw.mappers;

//import org.mapstruct.Mapper;
import ru.otus.hw.models.mongo.AuthorDocument;
import ru.otus.hw.models.mongo.BookDocument;
import ru.otus.hw.models.mongo.CommentDocument;
import ru.otus.hw.models.mongo.GenreDocument;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.models.h2.Genre;

//@Mapper(componentModel = "spring")
public interface MigrationMapper {
    AuthorDocument toAuthorDocument(Author author);

    GenreDocument toGenreDocument(Genre genre);

    BookDocument toBookDocument(Book book);

    CommentDocument toCommentDocument(Comment comment);
}
