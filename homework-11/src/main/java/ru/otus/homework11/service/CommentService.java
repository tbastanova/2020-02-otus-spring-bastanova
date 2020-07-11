package ru.otus.homework11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Comment;

public interface CommentService {
    Flux<Comment> findAll();

    Mono<Comment> findById(String id);

    Mono<Comment> save(Comment comment);

    Flux<Comment> findByBook_Id(String bookId);

    Mono<Comment> addBookComment(Book book, Comment comment);

    Mono<Void> deleteById(String id);
}
