package ru.otus.homework07.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework07.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa extends CrudRepository<Comment, Integer> {
    Optional<Comment> findById(long id);

    List<Comment> findAll();

    Comment save(Comment comment);

    void delete(Comment comment);

    boolean existsById(long id);

    List<Comment> findByBook_Id(long bookId);
}
