package ru.otus.homework10.service;

import ru.otus.homework10.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findByBook_Id(long bookId);

    Comment addBookComment(long bookId, String commentText);

    void deleteById(long id);
}
