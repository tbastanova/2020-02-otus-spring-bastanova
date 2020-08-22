package ru.otus.feedmysail.service;

import ru.otus.feedmysail.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(long id);
}
