package ru.otus.feedmysail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.feedmysail.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(long id);
}
