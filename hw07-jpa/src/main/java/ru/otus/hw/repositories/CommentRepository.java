package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBookId(long bookId);

    @Query("select c from Comment as c where c.book.id = :bookId or c.book.genre.id = :genreId")//
    List<Comment> findAllByBookIdOrGenreId(@Param("bookId") long bookId, @Param("genreId") long genreId);
}
