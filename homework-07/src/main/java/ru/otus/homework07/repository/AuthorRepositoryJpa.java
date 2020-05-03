package ru.otus.homework07.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework07.model.Author;
import ru.otus.homework07.model.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa extends CrudRepository<Author, Integer> {
    Optional<Author> findById(long id);

    List<Author> findAll();

    Author save(Author author);

    void delete(Author author);

    long count();

    boolean existsById(long id);

    List<Author> getAuthorsByBooks(Book book);
}
