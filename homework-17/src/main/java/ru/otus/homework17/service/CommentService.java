package ru.otus.homework17.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Book;
import ru.otus.homework17.model.Comment;

public interface CommentService {
    Flux<Comment> findAll();

    Mono<Comment> findById(String id);

    Mono<Comment> save(Comment comment);

    Flux<Comment> findByBook_Id(String bookId);

    Mono<Comment> addBookComment(Book book, Comment comment);

    Mono<Void> deleteById(String id);
}
