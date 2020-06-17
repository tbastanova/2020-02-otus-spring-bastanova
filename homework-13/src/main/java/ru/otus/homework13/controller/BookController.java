package ru.otus.homework13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.service.BookService;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", "/books"})
    public String listPage(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "listBook";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("bookId") long bookId, Model model) {
        model = bookService.getEditBookModel(bookId, model);
        return "editBook";
    }

    @PostMapping("/edit")
    public String saveBook(
            Book book,
            Model model
    ) {
        model.addAttribute(bookService.save(book));
        return "redirect:/";
    }

    @GetMapping("/addBook")
    public String create(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "addBook";
    }

    @PostMapping("/addBook")
    public String insertBook(
            Book book,
            Model model
    ) {
        bookService.save(book);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("bookId") long bookId) {
        bookService.deleteById(bookId);
        return "redirect:/";
    }

}
