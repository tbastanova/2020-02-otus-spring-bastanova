package ru.otus.homework08.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.exception.NoCategoryFoundException;
import ru.otus.homework08.model.Category;
import ru.otus.homework08.repository.CategoryRepository;
import ru.otus.homework08.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoCategoryFoundException(new Throwable()));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public boolean existsById(String id) {
        return categoryRepository.existsById(id);
    }
}
