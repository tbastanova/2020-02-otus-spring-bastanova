package ru.otus.homework16.service;

import ru.otus.homework16.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(long id);

    Category save(Category category);
}
