package ru.otus.homework11.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Category;
import ru.otus.homework11.repository.CategoryRepository;
import ru.otus.homework11.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Flux<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        categoryRepository.save(category).subscribe();
        return category;
    }

    @Transactional
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Mono<Long> count() {
        return categoryRepository.count();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return categoryRepository.existsById(id);
    }
}
