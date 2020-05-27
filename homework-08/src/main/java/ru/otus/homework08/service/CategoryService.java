package ru.otus.homework08.service;

import ru.otus.homework08.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(String id);

    Category save(Category category);

    void deleteById(String bookId);

    long count();

    boolean existsById(String id);

}
