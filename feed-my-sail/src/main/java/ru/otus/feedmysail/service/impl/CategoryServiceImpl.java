package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.feedmysail.exception.NoCategoryFoundException;
import ru.otus.feedmysail.model.Category;
import ru.otus.feedmysail.repository.CategoryRepositoryJpa;
import ru.otus.feedmysail.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepositoryJpa categoryRepositoryJpa;

    public CategoryServiceImpl(CategoryRepositoryJpa categoryRepositoryJpa) {
        this.categoryRepositoryJpa = categoryRepositoryJpa;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepositoryJpa.findAll();
    }

    @Override
    public Category findById(long id) {
        return categoryRepositoryJpa.findById(id).orElseThrow(() -> new NoCategoryFoundException(new Throwable()));
    }
}


