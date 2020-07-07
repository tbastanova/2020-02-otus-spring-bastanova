package ru.otus.homework11.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Category;
import ru.otus.homework11.service.BookService;

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
    public void deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
    }

    @PostMapping("/book")
    public void insertBook(
            Book book
    ) {
        bookService.save(book);
    }

    @PatchMapping("/book/{id}")
    public void updateBook(@PathVariable String id,
                           Book book
    ) {
        bookService.save(book);
    }

    @PutMapping("/book/{id}")
    public void putBook(@PathVariable String id,
                        Book book
    ) {
        bookService.save(book);
    }

    @DeleteMapping("/book/{bookId}/author/{authorId}")
    public void unlinkAuthor(@PathVariable("bookId") String bookId, @PathVariable("authorId") String authorId) {
        bookService.findById(bookId).subscribe(i -> bookService.removeBookAuthor(i, authorId));
    }

    @PutMapping("/book/{bookId}/author")
    public void linkAuthor(
            @PathVariable("bookId") String bookId,
            Author author
    ) {
        bookService.findById(bookId).subscribe(i -> bookService.setBookAuthor(i, author));
    }

    @DeleteMapping("/book/{bookId}/category/{categoryId}")
    public void unlinkCategory(@PathVariable("bookId") String bookId, @PathVariable("categoryId") String categoryId) {
        bookService.findById(bookId).subscribe(i -> bookService.removeBookCategory(i, categoryId));
    }

    @PutMapping("/book/{bookId}/category")
    public void linkCategory(
            @PathVariable("bookId") String bookId,
            Category category
    ) {
        bookService.findById(bookId).subscribe(i -> bookService.setBookCategory(i, category));
    }
}
