package ru.otus.homework06.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoBookFoundException;
import ru.otus.homework06.exception.NoCommentFoundException;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;
import ru.otus.homework06.repository.BookRepositoryJpa;
import ru.otus.homework06.repository.CommentRepositoryJpa;
import ru.otus.homework06.repository.impl.BookRepositoryJpaImpl;
import ru.otus.homework06.repository.impl.CommentRepositoryJpaImpl;
import ru.otus.homework06.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    public CommentServiceImpl(CommentRepositoryJpaImpl commentRepositoryJpa, BookRepositoryJpaImpl bookRepositoryJpa) {
        this.commentRepositoryJpa = commentRepositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Transactional
    public long addBookComment(long bookId, String commentText) {
        Comment comment = new Comment(0, commentText);
        comment.setBook(bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable())));
        return commentRepositoryJpa.insert(comment);
    }

    @Transactional
    public void updateBookId(long commentId, long bookId) {
        Comment comment = commentRepositoryJpa.findById(commentId).orElseThrow(() -> new NoCommentFoundException(new Throwable()));
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        commentRepositoryJpa.updateBookId(comment, book);
    }


}
