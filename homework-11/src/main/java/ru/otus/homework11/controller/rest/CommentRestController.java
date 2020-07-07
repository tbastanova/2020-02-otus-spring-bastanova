package ru.otus.homework11.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.service.BookService;
import ru.otus.homework11.service.CommentService;

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
    public void insertComment(
            @PathVariable("bookId") String bookId,
            Comment comment
    ) {
        bookService.findById(bookId).subscribe(i -> commentService.addBookComment(i, comment));
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable("id") String id) {
        commentService.deleteById(id);
    }
}
