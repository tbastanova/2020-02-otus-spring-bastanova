package ru.otus.homework11.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("bookId")
public class BookPageController {

    @GetMapping("/")
    public String listPage(Model model) {
        return "listBook";
    }

    @GetMapping("/editBook/{id}")
    public String editPage(@PathVariable String id, Model model) {
        model.addAttribute("bookId", id);
        return "editBook";
    }

}
