package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Author;
import ru.otus.hw.models.mongo.AuthorDocument;

@Component
public class AuthorConverter {

    public String authorToString(AuthorDocument author) {
        return "AuthorDocument | Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }

    public String authorToString(Author author) {
        return "Author | Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
