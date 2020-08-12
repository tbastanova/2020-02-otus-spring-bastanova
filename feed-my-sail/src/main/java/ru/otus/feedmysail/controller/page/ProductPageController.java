package ru.otus.feedmysail.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("userId")
public class ProductPageController {
    private final HttpSession session;

    public ProductPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/voteProduct")
    public String listProduct(Model model) {
        model.addAttribute("userId", 1);
        return "voteProduct";
    }


}
