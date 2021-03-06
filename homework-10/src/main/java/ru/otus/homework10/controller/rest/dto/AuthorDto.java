package ru.otus.homework10.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.homework10.model.Author;

@Data
@AllArgsConstructor
public class AuthorDto {
    private long id = -1;
    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

}
