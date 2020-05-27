package ru.otus.homework08.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.model.Category;
import ru.otus.homework08.repository.custom.CategoryRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String>, CategoryRepositoryCustom {
    Optional<Category> findById(String id);

    List<Category> findAll();

    Category save(Category category);

    void deleteById(String id);

    long count();

    boolean existsById(String id);
}
