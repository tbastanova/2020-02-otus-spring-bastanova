package ru.otus.homework16.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CategoryPageController {
    private final HttpSession session;

    public CategoryPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/listCategory")
    public String listCategory(Model model) {
        model.addAttribute("bookId", session.getAttribute("bookId"));
        return "listCategory";
    }
}
