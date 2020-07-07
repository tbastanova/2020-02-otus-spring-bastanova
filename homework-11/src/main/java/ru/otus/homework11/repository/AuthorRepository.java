package ru.otus.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Mono<Author> findById(String id);

    Flux<Author> findAll();

    Mono<Author> save(Author author);

    Mono<Void> deleteById(String id);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);

}
