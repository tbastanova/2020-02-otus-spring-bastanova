package ru.otus.homework08.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, String> {
    Optional<Comment> findById(String id);

    List<Comment> findAll();

    Comment save(Comment comment);

    List<Comment> findByBook_Id(String bookId);
}
