package ru.otus.homework10.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework10.exception.NoCategoryFoundException;
import ru.otus.homework10.model.Category;
import ru.otus.homework10.repository.CategoryRepositoryJpa;
import ru.otus.homework10.service.CategoryService;

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

    @Override
    public Category save(Category category) {
        return categoryRepositoryJpa.save(category);
    }
}
