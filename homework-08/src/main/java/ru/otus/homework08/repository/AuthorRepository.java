package ru.otus.homework08.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.repository.custom.AuthorRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, String>, AuthorRepositoryCustom {
    Optional<Author> findById(String id);

    List<Author> findAll();

    Author save(Author author);

    void deleteById(String id);

    long count();

    boolean existsById(String id);

}
