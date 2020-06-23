package ru.otus.homework14.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.homework14.model.jpa.CategoryJpa;

@Repository
public interface CategoryRepositoryJpa extends JpaRepository<CategoryJpa, Integer> {
}
