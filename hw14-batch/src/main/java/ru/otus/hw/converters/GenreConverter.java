package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Genre;
import ru.otus.hw.models.mongo.GenreDocument;

@Component
public class GenreConverter {
    public String genreToString(GenreDocument genre) {
        return "GenreDocument | Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
    public String genreToString(Genre genre) {
        return "Genre | Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
