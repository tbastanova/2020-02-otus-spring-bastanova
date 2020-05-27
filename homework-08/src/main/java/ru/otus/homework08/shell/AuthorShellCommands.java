package ru.otus.homework08.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework08.exception.NoAuthorFoundException;
import ru.otus.homework08.exception.NoBookFoundException;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.service.AuthorService;
import ru.otus.homework08.service.BookService;

@ShellComponent
public class AuthorShellCommands {
    private final AuthorService authorService;
    private final BookService bookService;

    private static final String DUMMY_ID = "0";
    private static final String NO_AUTHOR_FOUND_EXCEPTION_TEXT = "Автор с id = %s не найден";
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %s не найдена";

    public AuthorShellCommands(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ShellMethod(value = "Insert author", key = {"insertauthor", "ia"})
    public String insertAuthor(@ShellOption(defaultValue = "New Author") String authorName) {
        Author author = authorService.save(new Author(authorName));
        return String.format("Создан автор id = %s", author.getId());
    }

    @ShellMethod(value = "Get author by id", key = {"getauthor", "ga"})
    public String getAuthorById(@ShellOption String authorId) {
        try {
            Author author = authorService.findById(authorId);
            return String.format("Автор: id = %s, name = %s", author.getId(), author.getName());
        } catch (NoAuthorFoundException e) {
            return String.format(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
        }
    }

    @ShellMethod(value = "Update author", key = {"updateauthor", "ua"})
    public String updateAuthor(@ShellOption String authorId, String authorName) {
        authorService.save(new Author(authorId, authorName));
        try {
            return String.format("Автор: id = %s, name = %s", authorId, authorName);
        } catch (NoAuthorFoundException e) {
            return String.format(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
        }
    }

    @ShellMethod(value = "Delete author", key = {"deleteauthor", "da"})
    public String deleteAuthorById(@ShellOption String authorId) {
        authorService.deleteById(authorId);
        return String.format("Удален автор id = %s", authorId);
    }

    @ShellMethod(value = "Count author", key = {"countauthor", "ca"})
    public String countAuthor() {
        return String.format("Общее количество авторов: %d", authorService.count());
    }

    @ShellMethod(value = "Get all authors", key = {"getallauthor", "gaa"})
    public void getAllAuthor() {
        for (Author author :
                authorService.findAll()) {
            System.out.println(author.getId() + " | " + author.getName());
        }
    }

    @ShellMethod(value = "Get author by book id", key = {"getbookauthor", "gba"})
    public void getBookAuthor(@ShellOption String bookId) {
        try {
            Book book = bookService.findById(bookId);
            System.out.printf("Авторы книги \"%s\":", book.getName());
            System.out.println();
            for (Author author :
                    bookService.getBookAuthorsByBookId(bookId)) {
                System.out.println(author.getId() + " | " + author.getName());
            }
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        }
    }

}
