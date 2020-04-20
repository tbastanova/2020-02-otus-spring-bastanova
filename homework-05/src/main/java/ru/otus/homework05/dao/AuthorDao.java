package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Author;

import java.util.List;

public interface AuthorDao {
    long insert(Author author);

    Author getById(long id);

    int update(Author author);

    void deleteById(long id);

    long count();

    List<Author> getAll();

    List<Author> findAllUsed();

    boolean checkExists(long id);
}
