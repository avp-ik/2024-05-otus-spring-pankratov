package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.h2.Comment;
import ru.otus.hw.models.mongo.CommentDocument;

@Component
public class CommentConverter {

    public String commentToString(CommentDocument comment) {
        return "CommentDocument | Id: %s, Text: %s".formatted(comment.getId(), comment.getText());
    }

    public String commentToString(Comment comment) {
        return "Comment | Id: %s, Text: %s".formatted(comment.getId(), comment.getText());
    }
}