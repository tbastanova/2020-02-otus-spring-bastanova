package ru.otus.homework17.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Comment;
import ru.otus.homework17.service.BookService;
import ru.otus.homework17.service.CommentService;

@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final BookService bookService;

    public CommentRestController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @GetMapping("/book/{bookId}/comment")
    public Flux<Comment> getBookComments(@PathVariable("bookId") String bookId) {
        return commentService.findByBook_Id(bookId);
    }

    @PostMapping("/book/{bookId}/comment")
    public Mono<Comment> insertComment(
            @PathVariable("bookId") String bookId,
            Comment comment
    ) {
        return bookService.findById(bookId).flatMap(i -> commentService.addBookComment(i, comment));
    }

    @DeleteMapping("/comment/{id}")
    public Mono<Void> deleteComment(@PathVariable("id") String id) {
        return commentService.deleteById(id);
    }
}
