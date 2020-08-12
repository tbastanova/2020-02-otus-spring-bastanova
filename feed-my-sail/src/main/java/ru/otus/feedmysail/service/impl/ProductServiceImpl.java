package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.feedmysail.exception.NoProductFoundException;
import ru.otus.feedmysail.model.Product;
import ru.otus.feedmysail.repository.ProductRepositoryJpa;
import ru.otus.feedmysail.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryJpa productRepositoryJpa;

    public ProductServiceImpl(ProductRepositoryJpa productRepositoryJpa) {
        this.productRepositoryJpa = productRepositoryJpa;
    }

    @Override
    public List<Product> findAll() {
        return productRepositoryJpa.findAll();
    }

    @Override
    public Product findById(long id) {
        return productRepositoryJpa.findById(id).orElseThrow(() -> new NoProductFoundException(new Throwable()));
    }

    @Override
    public Product save(Product product) {
        return productRepositoryJpa.save(product);
    }
}
