package ru.otus.homework06.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoBookFoundException;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;
import ru.otus.homework06.repository.BookRepositoryJpa;
import ru.otus.homework06.repository.CommentRepositoryJpa;
import ru.otus.homework06.repository.impl.BookRepositoryJpaImpl;
import ru.otus.homework06.repository.impl.CommentRepositoryJpaImpl;
import ru.otus.homework06.service.CommentService;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepositoryJpa commentRepositoryJpa;
    private BookRepositoryJpa bookRepositoryJpa;

    public CommentServiceImpl(CommentRepositoryJpaImpl commentRepositoryJpa, BookRepositoryJpaImpl bookRepositoryJpa) {
        this.commentRepositoryJpa = commentRepositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Transactional
    public long addBookComment(long bookId, String commentText) {
        Optional<Book> book = bookRepositoryJpa.findById(bookId);

        if (book.isEmpty()) {
            throw new NoBookFoundException(new Throwable());
        }
        Comment comment = new Comment(0, commentText);
        comment.setBook(book.get());
        return commentRepositoryJpa.insert(comment);
    }
}
