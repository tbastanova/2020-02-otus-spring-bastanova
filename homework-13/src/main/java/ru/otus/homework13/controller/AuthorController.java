package ru.otus.homework13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework13.model.Author;
import ru.otus.homework13.service.AuthorService;
import ru.otus.homework13.service.BookService;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/listAuthor")
    public String listAuthor(@RequestParam("bookId") long bookId, Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("bookId", bookId);
        return "listAuthor";
    }

    @PostMapping("/linkAuthor")
    public String linkAuthor(
            @RequestParam("bookId") long bookId,
            @RequestParam("authorId") long authorId,
            Model model
    ) {
        bookService.setBookAuthor(bookId, authorId);
        model = bookService.getEditBookModel(bookId, model);
        return "redirect:/edit?bookId=" + bookId;
    }

    @PostMapping("/unlinkAuthor")
    public String unlinkAuthor(@RequestParam("bookId") long bookId, @RequestParam("authorId") long authorId, Model model) {
        bookService.removeBookAuthor(bookId, authorId);
        model = bookService.getEditBookModel(bookId, model);
        return "redirect:/edit?bookId=" + bookId;
    }

    @GetMapping("/addAuthor")
    public String createAuthor(
            @RequestParam("bookId") long bookId,
            Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("bookId", bookId);
        return "addAuthor";
    }

    @PostMapping("/addAuthor")
    public String insertAuthor(
            @RequestParam("bookId") long bookId,
            Author author,
            Model model
    ) {
        authorService.save(author);
        listAuthor(bookId, model);
        return "redirect:/listAuthor?bookId=" + bookId;
    }
}
