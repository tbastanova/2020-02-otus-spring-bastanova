package ru.otus.homework17.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Author;

public interface AuthorService {
    Flux<Author> findAll();

    Mono<Author> findById(String id);

    Mono<Author> save(Author author);

    Mono<Void> deleteById(String bookId);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);
}
