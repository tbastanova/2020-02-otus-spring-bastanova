package ru.otus.homework07.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryJpa extends CrudRepository<Category, Integer> {
    Optional<Category> findById(long id);

    List<Category> findAll();

    Category save(Category category);

    @Transactional
    void deleteById(long id);

    long count();

    boolean existsById(long id);

    List<Category> getCategoriesByBooks(Book book);
}
