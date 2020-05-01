package ru.otus.homework06.repository;

import ru.otus.homework06.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {
    Optional<Author> findById(long id);

    List<Author> findAll();

    long insert(Author author);

    void update(Author author);

    void deleteById(long id);

    long count();

    boolean checkExists(long id);

    List<Author> getAuthorsByBookId(long bookId);
}
