package ru.otus.homework17.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
    Mono<Category> findById(String id);

    Flux<Category> findAll();

    Mono<Category> save(Category category);

    Mono<Void> deleteById(String id);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);
}
