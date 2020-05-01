package ru.otus.homework06.service;

public interface CommentService {
    long addBookComment(long bookId, String commentText);

    void updateBookId(long commentId, long bookId);
}
