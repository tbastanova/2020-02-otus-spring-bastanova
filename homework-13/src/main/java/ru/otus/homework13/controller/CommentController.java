package ru.otus.homework13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework13.model.Comment;
import ru.otus.homework13.service.BookService;
import ru.otus.homework13.service.CommentService;

@Controller
public class CommentController {
    private final BookService bookService;
    private final CommentService commentService;

    public CommentController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public String addComment(
            @RequestParam("bookId") long bookId,
            Comment comment,
            Model model
    ) {
        commentService.addBookComment(bookId, comment.getText());
        model = bookService.getEditBookModel(bookId, model);
        return "redirect:/edit?bookId=" + bookId;
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("bookId") long bookId, @RequestParam("commentId") long commentId, Model model) {
        commentService.deleteById(commentId);
        model = bookService.getEditBookModel(bookId, model);
        return "redirect:/edit?bookId=" + bookId;
    }
}
