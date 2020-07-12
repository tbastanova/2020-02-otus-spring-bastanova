package ru.otus.homework17.service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Author;
import ru.otus.homework17.model.Book;
import ru.otus.homework17.model.Category;

public interface BookService {

    Flux<Book> findAll();

    Mono<Book> findById(String id);

    Mono<Book> save(Book book);

    Mono<Void> deleteById(String bookId);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);

    Mono<Book> setBookAuthor(Book book, Author author);

    Mono<Book> removeBookAuthor(Book book, String authorId);

    Mono<Book> setBookCategory(Book book, Category category);

    Mono<Book> removeBookCategory(Book book, String categoryId);
}
