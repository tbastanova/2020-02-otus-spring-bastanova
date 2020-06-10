package ru.otus.homework10.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.homework10.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BookDto {
    private long id = -1;
    private String name;
    private List<AuthorDto> authors;
    private List<CategoryDto> categories;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthors().stream().map(AuthorDto::toDto).collect(Collectors.toList()), book.getCategories().stream().map(CategoryDto::toDto).collect(Collectors.toList()));
    }

}
