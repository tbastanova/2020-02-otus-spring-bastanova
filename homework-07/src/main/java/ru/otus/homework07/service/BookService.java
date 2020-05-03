package ru.otus.homework07.service;


import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Comment;

import java.util.List;

public interface BookService {
    String getBookToString(Book book);

    String getBookCommentToString(Book book, List<Comment> commentList);

    String getBookByIdToString(long bookId);

    String getAllBooksToString();

    void setBookAuthor(long bookId, long authorId);

    Book setBookCategory(long bookId, long categoryId);
}
