package ru.otus.homework06.repository;

import ru.otus.homework06.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryJpa {
    Optional<Category> findById(long id);

    List<Category> findAll();

    long insert(Category category);

    void update(Category category);

    void deleteById(long id);

    long count();

    boolean checkExists(long id);

    List<Category> getCategoriesByBookId(long bookId);
}
