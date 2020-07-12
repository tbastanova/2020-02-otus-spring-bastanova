package ru.otus.homework17.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Book> findById(String id);

    Flux<Book> findAll();

    Mono<Book> save(Mono<Book> book);

    Mono<Void> deleteById(String id);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);

}
