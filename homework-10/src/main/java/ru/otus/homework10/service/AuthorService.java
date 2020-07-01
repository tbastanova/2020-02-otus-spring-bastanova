package ru.otus.homework10.service;

import ru.otus.homework10.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(long id);

    Author save(Author author);

}
