package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Category;

import java.util.List;

public interface BookDao {
    long insert(Book book);

    Book getById(long id);

    int update(Book book);

    void deleteById(long id);

    long count();

    List<Book> getAll();

    int addBookAuthor(long bookId, long authorId);

    List<Author> getBookAuthor(long bookId);

    int addBookCategory(long bookId, long categoryId);

    List<Category> getBookCategory(long bookId);
}
