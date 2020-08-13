package ru.otus.feedmysail.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.feedmysail.model.Category;
import ru.otus.feedmysail.model.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryJpa extends CrudRepository<Category, Long> {
    List<Category> findAll();

    Optional<Category> findById(long id);
}
