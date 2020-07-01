package ru.otus.homework16.service;

import ru.otus.homework16.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findByBook_Id(long bookId);

    Comment addBookComment(long bookId, String commentText);

    void deleteById(long id);
}
