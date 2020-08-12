package ru.otus.feedmysail.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.feedmysail.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryJpa extends CrudRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(long id);
}
