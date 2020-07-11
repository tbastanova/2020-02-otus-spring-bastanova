package ru.otus.homework11.controller.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    public Mono<Author> insertAuthor(
            Author author
    ) {
        return authorService.save(author);
    }
}
