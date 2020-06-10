package ru.otus.homework10.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.controller.rest.dto.CommentDto;
import ru.otus.homework10.model.Comment;
import ru.otus.homework10.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/book/{bookId}/comments")
    public List<CommentDto> getBookComments(@PathVariable("bookId") long bookId) {
        return commentService.findByBook_Id(bookId).stream().map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/book/{bookId}/comment")
    public void insertComment(
            @PathVariable("bookId") long bookId,
            Comment comment
    ) {
        commentService.addBookComment(bookId, comment.getText());
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable("id") long id) {
        commentService.deleteById(id);
    }
}
