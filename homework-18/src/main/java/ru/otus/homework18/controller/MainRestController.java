package ru.otus.homework18.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework18.service.MainService;

@RestController
public class MainRestController {
    private final MainService mainService;

    public MainRestController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String helloWorld(Model model) {
        return mainService.helloWord();
    }
}
