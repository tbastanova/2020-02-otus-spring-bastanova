package ru.otus.homework06.repository;

import ru.otus.homework06.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Optional<Book> findById(long id);

    List<Book> findAll();

    long insert(Book book);

    void update(Book book);

    void deleteById(long id);

    long count();

    boolean checkExists(long id);

    void addBookAuthor(long bookId, long authorId);

    void addBookCategory(long bookId, long categoryId);
}
