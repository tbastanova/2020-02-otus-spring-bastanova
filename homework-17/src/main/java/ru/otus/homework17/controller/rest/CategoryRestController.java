package ru.otus.homework17.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Category;
import ru.otus.homework17.service.CategoryService;

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
    public Mono<Category> insertCategory(
            Category category
    ) {
        return categoryService.save(category);
    }
}
