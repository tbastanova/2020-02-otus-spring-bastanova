package ru.otus.homework13.service;

import ru.otus.homework13.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(long id);

    Category save(Category category);
}
