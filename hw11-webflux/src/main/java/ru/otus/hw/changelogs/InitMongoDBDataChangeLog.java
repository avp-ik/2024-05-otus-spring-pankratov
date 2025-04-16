package ru.otus.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private final List<Mono<Author>> authors = new ArrayList<>();

    private final List<Mono<Genre>> genres = new ArrayList<>();

    private final List<Mono<Book>> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "pankratov", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "pankratov", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        authors.add(repository.save(new Author("Пушкин А.С.")));
        authors.add(repository.save(new Author("Лермонов М.Ю.")));
        authors.add(repository.save(new Author("Есенин С.А.")));
        authors.add(repository.save(new Author("Блок А.А.")));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "pankratov", runAlways = true)
    public void initGenres(GenreRepository repository) {
        genres.add(repository.save(new Genre("Детектив")));
        genres.add(repository.save(new Genre("Фантастика")));
        genres.add(repository.save(new Genre("Любовный роман")));
        genres.add(repository.save(new Genre("Биография")));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "pankratov", runAlways = true)
    public void initBooks(BookRepository repository) {
        books.add(repository.save(new Book("Новое время", authors.get(0).block(), genres.get(0).block())));
        books.add(repository.save(new Book("Вечный зов", authors.get(1).block(), genres.get(1).block())));
        books.add(repository.save(new Book("Золотой жук", authors.get(2).block(), genres.get(2).block())));
        books.add(repository.save(new Book("Аватар", authors.get(3).block(), genres.get(3).block())));
    }

    @ChangeSet(order = "004", id = "initComments", author = "pankratov", runAlways = true)
    public void initComments(CommentRepository repository) {
        for (Mono<Book> monoBook : books) {
            Book currentBook = monoBook.block();
            repository.save(new Comment("Отличная книга!", currentBook));
            repository.save(new Comment("Пример литературы мирового уровня!", currentBook));
            repository.save(new Comment("Читал в рамках школьной программы!", currentBook));
        }
    }
}