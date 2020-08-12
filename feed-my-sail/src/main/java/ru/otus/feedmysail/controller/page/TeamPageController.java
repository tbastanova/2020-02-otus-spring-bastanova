package ru.otus.feedmysail.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("userId")
public class TeamPageController {
    private final HttpSession session;

    public TeamPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/myTeam/{id}")
    public String teamPage(@PathVariable long id, Model model) {
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("teamId", id);
        return "myTeam";
    }
}
