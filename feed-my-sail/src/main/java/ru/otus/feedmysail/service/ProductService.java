package ru.otus.feedmysail.service;

import ru.otus.feedmysail.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(long id);

    Product save(Product product);
}
