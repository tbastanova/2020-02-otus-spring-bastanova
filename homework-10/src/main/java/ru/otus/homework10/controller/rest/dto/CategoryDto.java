package ru.otus.homework10.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.homework10.model.Category;

@Data
@AllArgsConstructor
public class CategoryDto {
    private long id = -1;
    private String name;

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

}
