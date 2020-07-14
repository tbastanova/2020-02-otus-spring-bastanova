package ru.otus.homework17.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Author;
import ru.otus.homework17.model.Book;
import ru.otus.homework17.model.Category;
import ru.otus.homework17.service.BookService;

@RestController
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public Flux<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/book/{id}")
    public Mono<Book> getBook(@PathVariable String id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/book/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        return bookService.deleteById(id);
    }

    @PostMapping("/book")
    public Mono<Book> insertBook(
            Book book
    ) {
        return bookService.save(book);
    }

    @PatchMapping("/book/{id}")
    public Mono<Book> updateBook(@PathVariable String id,
                                 Book book
    ) {
        return bookService.save(book);
    }

    @PutMapping("/book/{id}")
    public Mono<Book> putBook(@PathVariable String id,
                              Book book
    ) {
        return bookService.save(book);
    }

    @DeleteMapping("/book/{bookId}/author/{authorId}")
    public Mono<Book> unlinkAuthor(@PathVariable("bookId") String bookId, @PathVariable("authorId") String authorId) {
        return bookService.findById(bookId).flatMap(i -> bookService.removeBookAuthor(i, authorId));
    }

    @PutMapping("/book/{bookId}/author")
    public Mono<Book> linkAuthor(
            @PathVariable("bookId") String bookId,
            Author author
    ) {
        return bookService.findById(bookId).flatMap(i -> bookService.setBookAuthor(i, author));
    }

    @DeleteMapping("/book/{bookId}/category/{categoryId}")
    public Mono<Book> unlinkCategory(@PathVariable("bookId") String bookId, @PathVariable("categoryId") String categoryId) {
        return bookService.findById(bookId).flatMap(i -> bookService.removeBookCategory(i, categoryId));
    }

    @PutMapping("/book/{bookId}/category")
    public Mono<Book> linkCategory(
            @PathVariable("bookId") String bookId,
            Category category
    ) {
        return bookService.findById(bookId).flatMap(i -> bookService.setBookCategory(i, category));
    }
}
