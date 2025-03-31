package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllByBookId(long bookId);

    List<CommentDto> findAllByCommentText(String commentText);

    CommentDto findCommentById(long id);

    CommentDto insert(String text, long authorId);

    void deleteById(long id);

    CommentDto update(long id, String text);

}
