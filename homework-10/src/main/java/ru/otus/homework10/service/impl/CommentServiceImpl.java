package ru.otus.homework10.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework10.exception.NoBookFoundException;
import ru.otus.homework10.model.Comment;
import ru.otus.homework10.repository.BookRepositoryJpa;
import ru.otus.homework10.repository.CommentRepositoryJpa;
import ru.otus.homework10.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    public CommentServiceImpl(CommentRepositoryJpa commentRepositoryJpa, BookRepositoryJpa bookRepositoryJpa) {
        this.commentRepositoryJpa = commentRepositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Override
    public List<Comment> findByBook_Id(long bookId) {
        return commentRepositoryJpa.findByBook_Id(bookId);
    }

    @Transactional
    public Comment addBookComment(long bookId, String commentText) {
        Comment comment = new Comment(0, commentText);
        comment.setBook(bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable())));
        return commentRepositoryJpa.save(comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepositoryJpa.deleteById(id);
    }
}
