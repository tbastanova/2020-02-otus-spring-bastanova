package ru.otus.homework11.service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Category;

public interface BookService {

    Flux<Book> findAll();

    Mono<Book> findById(String id);

    Book save(Book book);

    void deleteById(String bookId);

    Mono<Long> count();

    Mono<Boolean> existsById(String id);

    Book setBookAuthor(Book book, Author author);

    Book removeBookAuthor(Book book, String authorId);

    Book setBookCategory(Book book, Category category);

    Book removeBookCategory(Book book, String categoryId);
}
