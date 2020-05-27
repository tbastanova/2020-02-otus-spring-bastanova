package ru.otus.homework08.service;


import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Category;
import ru.otus.homework08.model.Comment;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(String id);

    Book save(Book book);

    void deleteById(String bookId);

    long count();

    boolean existsById(String id);

    Book setBookAuthor(String bookId, String authorId);

    Book removeBookAuthor(String bookId, String authorId);

    Book setBookCategory(String bookId, String categoryId);

    Book removeBookCategory(String bookId, String authorId);

    String getBookByIdToString(String bookId);

    String getAllBooksToString();

    String getBookCommentToString(Book book, List<Comment> commentList);

    List<Author> getBookAuthorsByBookId(String bookId);

    List<Category> getBookCategoriesByBookId(String bookId);
}
