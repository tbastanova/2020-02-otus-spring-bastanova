package ru.otus.feedmysail.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.feedmysail.service.impl.UserDetailsServiceImpl;

@Controller
public class ProductPageController {
    private final UserDetailsServiceImpl userDetailsService;

    public ProductPageController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/voteProduct")
    public String listProduct(Model model) {
        model.addAttribute("userId", userDetailsService.getUserId());
        return "voteProduct";
    }


}
