package ru.otus.feedmysail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.feedmysail.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(long id);
}
