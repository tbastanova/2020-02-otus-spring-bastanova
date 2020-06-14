package ru.otus.homework12.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework12.model.Comment;

import java.util.List;

public interface CommentRepositoryJpa extends CrudRepository<Comment, Integer> {
    Comment save(Comment comment);

    void deleteById(long id);

    List<Comment> findByBook_Id(long bookId);
}
