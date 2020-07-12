package ru.otus.homework17.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorPageController {

    @GetMapping("/listAuthor")
    public String listAuthor(@RequestParam("bookId") String bookId, Model model) {
        model.addAttribute("bookId", bookId);

        return "listAuthor";
    }
}
