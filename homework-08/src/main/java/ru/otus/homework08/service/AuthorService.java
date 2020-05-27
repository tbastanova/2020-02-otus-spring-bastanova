package ru.otus.homework08.service;

import ru.otus.homework08.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(String id);

    Author save(Author author);

    void deleteById(String bookId);

    long count();

    boolean existsById(String id);
}
