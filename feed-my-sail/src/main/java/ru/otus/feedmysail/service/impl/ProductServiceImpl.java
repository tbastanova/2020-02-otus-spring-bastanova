package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.feedmysail.exception.NoProductFoundException;
import ru.otus.feedmysail.model.Product;
import ru.otus.feedmysail.repository.ProductRepository;
import ru.otus.feedmysail.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoProductFoundException(new Throwable()));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
