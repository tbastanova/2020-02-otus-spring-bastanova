package ru.otus.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Mono<Comment> findById(String id);

    Flux<Comment> findAll();

    Mono<Comment> save(Comment comment);

    Flux<Comment> findByBook_Id(String bookId);
}
