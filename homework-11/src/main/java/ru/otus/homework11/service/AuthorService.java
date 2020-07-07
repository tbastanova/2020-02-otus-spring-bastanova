package ru.otus.homework11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Author;

public interface AuthorService {
    Flux<Author> findAll();

    Mono<Author> findById(String id);

    Author save(Author author);

    void deleteById(String bookId);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);
}
