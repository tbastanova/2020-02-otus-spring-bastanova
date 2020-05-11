package ru.otus.homework09.service;

import ru.otus.homework09.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(long id);

    Author save(Author author);
}
