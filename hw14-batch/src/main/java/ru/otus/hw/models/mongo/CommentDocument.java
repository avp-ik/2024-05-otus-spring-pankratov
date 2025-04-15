package ru.otus.hw.models.mongo;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class CommentDocument {
    @Id
    private String id;

    private String text;

    @DBRef(lazy = true)
    private BookDocument book;

    public CommentDocument(String text, BookDocument book) {
        this.text = text;
        this.book = book;
    }
}