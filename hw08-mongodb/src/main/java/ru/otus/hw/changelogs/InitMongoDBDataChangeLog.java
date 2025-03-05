package ru.otus.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
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

    private final List<Author> authors = new ArrayList<>();

    private final List<Genre> genres = new ArrayList<>();

    private final List<Book> books = new ArrayList<>();

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
        books.add(repository.save(new Book("Новое время", authors.get(0), genres.get(0))));
        books.add(repository.save(new Book("Вечный зов", authors.get(1), genres.get(1))));
        books.add(repository.save(new Book("Золотой жук", authors.get(2), genres.get(2))));
        books.add(repository.save(new Book("Аватар", authors.get(2), genres.get(2))));
    }

    @ChangeSet(order = "004", id = "initComments", author = "pankratov", runAlways = true)
    public void initComments(CommentRepository repository) {
        for (Book currentBook : books) {
            repository.save(new Comment("Отличная книга!", currentBook));
            repository.save(new Comment("Пример литературы мирового уровня!", currentBook));
            repository.save(new Comment("Читал в рамках школьной программы!", currentBook));
        }
    }
}