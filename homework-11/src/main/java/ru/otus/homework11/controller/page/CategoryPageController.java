package ru.otus.homework11.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryPageController {

    @GetMapping("/listCategory")
    public String listCategory(@RequestParam("bookId") String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "listCategory";
    }
}
