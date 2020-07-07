package ru.otus.homework11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Category;

public interface CategoryService {
    Flux<Category> findAll();

    Mono<Category> findById(String id);

    Category save(Category category);

    void deleteById(String bookId);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);

}
