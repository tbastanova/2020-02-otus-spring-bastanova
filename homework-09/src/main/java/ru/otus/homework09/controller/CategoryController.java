package ru.otus.homework09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework09.model.Category;
import ru.otus.homework09.service.BookService;
import ru.otus.homework09.service.CategoryService;

import java.util.List;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    public CategoryController(CategoryService categoryService, BookService bookService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @GetMapping("/listCategory")
    public String listCategory(@RequestParam("bookId") long bookId, Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("bookId", bookId);
        return "listCategory";
    }

    @PostMapping("/linkCategory")
    public String linkCategory(
            @RequestParam("bookId") long bookId,
            @RequestParam("categoryId") long categoryId,
            Model model
    ) {
        bookService.setBookCategory(bookId, categoryId);
        model = bookService.getEditBookModel(bookId, model);
        return "redirect:/edit?bookId=" + bookId;
    }

    @PostMapping("/unlinkCategory")
    public String unlinkCategory(@RequestParam("bookId") long bookId, @RequestParam("categoryId") long categoryId, Model model) {
        bookService.removeBookCategory(bookId, categoryId);
        model = bookService.getEditBookModel(bookId, model);
        return "redirect:/edit?bookId=" + bookId;
    }

    @GetMapping("/addCategory")
    public String createCategory(
            @RequestParam("bookId") long bookId,
            Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("bookId", bookId);
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String insertCategory(
            @RequestParam("bookId") long bookId,
            Category category,
            Model model
    ) {
        categoryService.save(category);
        listCategory(bookId, model);
        return "redirect:/listCategory?bookId=" + bookId;
    }
}
