package ru.otus.homework06.service;


import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;

import java.util.List;

public interface BookService {
    String getBookToString(Book book);

    String getBookCommentToString(Book book, List<Comment> commentList);

    String getBookByIdToString(long bookId);

    String getAllBooksToString();
}
