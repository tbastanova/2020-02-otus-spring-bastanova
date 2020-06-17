package ru.otus.homework13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework13.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryJpa extends CrudRepository<Category, Integer> {
    Optional<Category> findById(long id);

    List<Category> findAll();

    Category save(Category category);
}
