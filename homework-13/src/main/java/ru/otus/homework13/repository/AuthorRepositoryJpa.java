package ru.otus.homework13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework13.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa extends CrudRepository<Author, Integer> {
    Optional<Author> findById(long id);

    List<Author> findAll();

    Author save(Author author);
}
