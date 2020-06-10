package ru.otus.homework10.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework10.controller.rest.dto.CategoryDto;
import ru.otus.homework10.model.Category;
import ru.otus.homework10.service.CategoryService;

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

    @PostMapping("/category")
    public void insertCategory(
            Category category
    ) {
        categoryService.save(category);
    }
}
