package ru.otus.homework08.service;

import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findById(String id);

    Comment save(Comment author);

    List<Comment> findByBook_Id(String bookId);

    Comment addBookComment(Book book, String commentText);

    void updateBookId(String commentId, Book book);

    void deleteById(String id);
}
