package ru.otus.homework12.service;

import ru.otus.homework12.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(long id);

    Author save(Author author);
}
