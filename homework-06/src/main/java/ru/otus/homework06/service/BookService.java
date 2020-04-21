package ru.otus.homework06.service;


import ru.otus.homework06.model.Book;

public interface BookService {
    String getBookToString(Book book);

    String getBookCommentToString(long bookId);
}
