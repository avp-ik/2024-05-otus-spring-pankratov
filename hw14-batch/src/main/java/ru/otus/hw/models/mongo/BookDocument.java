package ru.otus.hw.models.mongo;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class BookDocument {
    @Id
    private String id;

    private String title;

    private AuthorDocument author;

    private GenreDocument genre;

    public BookDocument(String title, AuthorDocument author, GenreDocument genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}