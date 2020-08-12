package ru.otus.feedmysail.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserPageController {
    private final HttpSession session;

    public UserPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/myProfile")
    public String userProfile(Model model) {
//        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userId", 1);
        return "myProfile";
    }

}
