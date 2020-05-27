package ru.otus.homework08.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.repository.custom.BookRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, String>, BookRepositoryCustom {

    Optional<Book> findById(String id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(String id);

    long count();

    boolean existsById(String id);

}
