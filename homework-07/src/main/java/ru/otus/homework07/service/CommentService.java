package ru.otus.homework07.service;

import ru.otus.homework07.model.Comment;

public interface CommentService {
    Comment addBookComment(long bookId, String commentText);

    void updateBookId(long commentId, long bookId);
}
