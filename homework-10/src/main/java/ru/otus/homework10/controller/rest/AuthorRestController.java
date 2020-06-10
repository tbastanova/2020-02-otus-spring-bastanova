package ru.otus.homework10.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework10.controller.rest.dto.AuthorDto;
import ru.otus.homework10.model.Author;
import ru.otus.homework10.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.findAll().stream().map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/author")
    public void insertAuthor(
            Author author
    ) {
        authorService.save(author);
    }
}
