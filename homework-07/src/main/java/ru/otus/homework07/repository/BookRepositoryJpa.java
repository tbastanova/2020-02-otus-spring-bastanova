package ru.otus.homework07.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework07.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa extends CrudRepository<Book, Integer> {

    Optional<Book> findById(long id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id);

    long count();

    boolean existsById(long id);

}
