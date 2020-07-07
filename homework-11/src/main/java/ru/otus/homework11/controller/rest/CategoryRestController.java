package ru.otus.homework11.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework11.model.Category;
import ru.otus.homework11.service.CategoryService;

@RestController
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public Flux<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @PostMapping("/category")
    public void insertCategory(
            Category category
    ) {
        categoryService.save(category);
    }
}
