package ru.otus.homework11.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.service.AuthorService;

@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author")
    public Flux<Author> getAllAuthors() {
        return authorService.findAll();
    }

    @PostMapping("/author")
    public void insertAuthor(
            Author author
    ) {
        authorService.save(author);
    }
}
