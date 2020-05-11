package ru.otus.homework09.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework09.model.Comment;

import java.util.List;

public interface CommentRepositoryJpa extends CrudRepository<Comment, Integer> {
    Comment save(Comment comment);

    void deleteById(long id);

    List<Comment> findByBook_Id(long bookId);
}
