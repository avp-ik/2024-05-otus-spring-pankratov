package ru.otus.hw.listeners;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@Configuration
@RequiredArgsConstructor
public class BookCascadeDeleteMongoEventListenerConfig
        extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
        Object source = event.getSource();
        if (source == null) {
            return;
        }
        if (event.getType().getName() == Book.class.getName()) {
            String bookId = ((Document) source).get("_id").toString();
            Query q = new Query(Criteria.where("book.$id").in(new ObjectId(bookId)));
            mongoOperations.findAllAndRemove(q, Comment.class);
        }
    }
}