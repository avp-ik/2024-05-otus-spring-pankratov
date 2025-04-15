package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.models.h2.Book;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.models.mongo.AuthorDocument;
import ru.otus.hw.models.mongo.BookDocument;
import ru.otus.hw.models.mongo.CommentDocument;
import ru.otus.hw.models.mongo.GenreDocument;

@Component
public class MigrationMapperImpl implements MigrationMapper {
    public MigrationMapperImpl() {
    }

    public AuthorDocument toAuthorDocument(Author author) {
        if (author == null) {
            return null;
        } else {
            AuthorDocument authorDocument = new AuthorDocument();
            authorDocument.setId(String.valueOf(author.getId()));
            authorDocument.setFullName(author.getFullName());
            return authorDocument;
        }
    }

    public GenreDocument toGenreDocument(Genre genre) {
        if (genre == null) {
            return null;
        } else {
            GenreDocument genreDocument = new GenreDocument();
            genreDocument.setId(String.valueOf(genre.getId()));
            genreDocument.setName(genre.getName());
            return genreDocument;
        }
    }

    public BookDocument toBookDocument(Book book) {
        if (book == null) {
            return null;
        } else {
            BookDocument bookDocument = new BookDocument();
            bookDocument.setId(String.valueOf(book.getId()));
            bookDocument.setTitle(book.getTitle());
            bookDocument.setAuthor(this.toAuthorDocument(book.getAuthor()));
            bookDocument.setGenre(this.toGenreDocument(book.getGenre()));
            return bookDocument;
        }
    }

    public CommentDocument toCommentDocument(Comment comment) {
        if (comment == null) {
            return null;
        } else {
            CommentDocument commentDocument = new CommentDocument();
            commentDocument.setId(String.valueOf(comment.getId()));
            commentDocument.setText(comment.getText());
            commentDocument.setBook(this.toBookDocument(comment.getBook()));
            return commentDocument;
        }
    }
}
