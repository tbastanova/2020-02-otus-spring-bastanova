package ru.otus.homework06.repository;

import ru.otus.homework06.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
    Optional<Comment> findById(long id);

    List<Comment> findAll();

    long insert(Comment comment);

    void update(Comment comment);

    void deleteById(long id);

    boolean checkExists(long id);

    List<Comment> findByBookId(long id);

    void updateBookId(long commentId, long bookId);
}
