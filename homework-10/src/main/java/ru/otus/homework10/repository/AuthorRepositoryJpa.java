package ru.otus.homework10.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework10.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa extends CrudRepository<Author, Integer> {
    Optional<Author> findById(long id);

    List<Author> findAll();

    Author save(Author author);

}
