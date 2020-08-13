package ru.otus.feedmysail.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.feedmysail.model.Category;
import ru.otus.feedmysail.model.Product;

@Data
@AllArgsConstructor
public class CategoryDto {
    private long id = -1;
    private String name;

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

}
