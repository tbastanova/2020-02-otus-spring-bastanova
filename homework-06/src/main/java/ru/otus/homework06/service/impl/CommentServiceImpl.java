package ru.otus.homework06.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoBookFoundException;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;
import ru.otus.homework06.repository.impl.BookRepositoryJpaImpl;
import ru.otus.homework06.repository.impl.CommentRepositoryJpaImpl;
import ru.otus.homework06.service.CommentService;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private static final long DUMMY_ID = 0;

    @Autowired
    private CommentRepositoryJpaImpl commentRepositoryJpa;

    @Autowired
    private BookRepositoryJpaImpl bookRepositoryJpa;

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
