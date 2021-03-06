package ru.otus.homework10.controller.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.controller.rest.dto.BookDto;
import ru.otus.homework10.model.Author;
import ru.otus.homework10.model.Book;
import ru.otus.homework10.model.Category;
import ru.otus.homework10.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookDto getBook(@PathVariable long id) {
        return BookDto.toDto(bookService.findById(id));
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }

    @PostMapping("/book")
    public void insertBook(
            Book book,
            Model model
    ) {
        bookService.save(book);
    }

    @PatchMapping("/book/{id}")
    public void updateBook(@PathVariable long id,
                           Book book
    ) {
        bookService.save(book);
    }

    @PutMapping("/book/{id}")
    public void putBook(@PathVariable long id,
                        Book book
    ) {
        bookService.save(book);
    }

    @DeleteMapping("/book/{bookId}/author/{authorId}")
    public void unlinkAuthor(@PathVariable("bookId") long bookId, @PathVariable("authorId") long authorId) {
        bookService.removeBookAuthor(bookId, authorId);
    }

    @PutMapping("/book/{bookId}/author")
    public void linkAuthor(
            @PathVariable("bookId") long bookId,
            Author author
    ) {
        bookService.setBookAuthor(bookId, author.getId());
    }

    @DeleteMapping("/book/{bookId}/category/{categoryId}")
    public void unlinkCategory(@PathVariable("bookId") long bookId, @PathVariable("categoryId") long categoryId) {
        bookService.removeBookCategory(bookId, categoryId);
    }

    @PutMapping("/book/{bookId}/category")
    public void linkCategory(
            @PathVariable("bookId") long bookId,
            Category category
    ) {
        bookService.setBookCategory(bookId, category.getId());
    }
}
