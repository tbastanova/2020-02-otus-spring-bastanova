package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoProductFoundException(new Throwable()));
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
