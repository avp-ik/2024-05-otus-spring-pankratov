package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.DtoMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final DtoMapper dtoMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllByBookId(long bookId) {
        return commentRepository.findAllByBookId(bookId).stream()
                .map(dtoMapper::toCommentDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllByCommentText(String commentText) {
        return commentRepository.findAllByCommentText(commentText).stream()
                .map(dtoMapper::toCommentDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto findCommentById(long id) {
        return commentRepository.findById(id).map(dtoMapper::toCommentDto).get();
    }

    @Transactional
    @Override
    public CommentDto insert(String text, long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        Comment comment = new Comment();
        comment.setText(text);
        comment.setBook(book);
        return dtoMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public CommentDto update(long id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setText(text);
        return dtoMapper.toCommentDto(commentRepository.save(comment));
    }
}
