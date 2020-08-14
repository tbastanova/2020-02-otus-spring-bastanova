package ru.otus.feedmysail.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.feedmysail.controller.rest.dto.CategoryDto;
import ru.otus.feedmysail.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll().stream().map(CategoryDto::toDto)
                .collect(Collectors.toList());
    }
}
