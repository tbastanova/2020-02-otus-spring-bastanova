package ru.otus.homework07.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework07.exception.NoBookFoundException;
import ru.otus.homework07.exception.NoCommentFoundException;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Comment;
import ru.otus.homework07.repository.BookRepositoryJpa;
import ru.otus.homework07.repository.CommentRepositoryJpa;
import ru.otus.homework07.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    public CommentServiceImpl(CommentRepositoryJpa commentRepositoryJpa, BookRepositoryJpa bookRepositoryJpa) {
        this.commentRepositoryJpa = commentRepositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Transactional
    public Comment addBookComment(long bookId, String commentText) {
        Comment comment = new Comment(0, commentText);
        comment.setBook(bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable())));
        return commentRepositoryJpa.save(comment);
    }

    @Transactional
    public void updateBookId(long commentId, long bookId) {
        Comment comment = commentRepositoryJpa.findById(commentId).orElseThrow(() -> new NoCommentFoundException(new Throwable()));
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        comment.setBook(book);
        commentRepositoryJpa.save(comment);
    }


}
