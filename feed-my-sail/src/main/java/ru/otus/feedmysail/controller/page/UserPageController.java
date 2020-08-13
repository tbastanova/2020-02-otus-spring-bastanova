package ru.otus.feedmysail.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.feedmysail.service.impl.UserDetailsServiceImpl;

import java.security.Principal;

@Controller
public class UserPageController {

    private final UserDetailsServiceImpl userDetailsService;

    public UserPageController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping({"/","/myProfile"})
    public String userProfile(Model model) {
        model.addAttribute("userId", userDetailsService.getUserId());
//        model.addAttribute("userFullName", userDetailsService.getUserFullName());
        return "myProfile";
    }

}
