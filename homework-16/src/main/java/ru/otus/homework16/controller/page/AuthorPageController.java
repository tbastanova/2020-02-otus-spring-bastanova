package ru.otus.homework16.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthorPageController {
    private final HttpSession session;

    public AuthorPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/listAuthor")
    public String listAuthor(Model model) {
        model.addAttribute("bookId", session.getAttribute("bookId"));
        return "listAuthor";
    }
}
