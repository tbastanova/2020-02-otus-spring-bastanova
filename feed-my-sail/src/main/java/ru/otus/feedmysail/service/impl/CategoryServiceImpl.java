package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.feedmysail.exception.NoCategoryFoundException;
import ru.otus.feedmysail.model.Category;
import ru.otus.feedmysail.repository.CategoryRepository;
import ru.otus.feedmysail.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoCategoryFoundException(new Throwable()));
    }
}


