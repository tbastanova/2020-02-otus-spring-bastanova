package ru.otus.homework07.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework07.exception.NoAuthorFoundException;
import ru.otus.homework07.exception.NoBookFoundException;
import ru.otus.homework07.model.Author;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.repository.AuthorRepositoryJpa;
import ru.otus.homework07.repository.BookRepositoryJpa;

@ShellComponent
public class AuthorShellCommands {
    private final AuthorRepositoryJpa repositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    private static final long DUMMY_ID = -1;
    private static final String NO_AUTHOR_FOUND_EXCEPTION_TEXT = "Автор с id = %d не найден";
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %d не найдена";

    public AuthorShellCommands(AuthorRepositoryJpa repositoryJpa, BookRepositoryJpa bookRepositoryJpa) {
        this.repositoryJpa = repositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @ShellMethod(value = "Insert author", key = {"insertauthor", "ia"})
    public String insertAuthor(@ShellOption(defaultValue = "New Author") String authorName) {
        Author author = repositoryJpa.save(new Author(DUMMY_ID, authorName));
        return String.format("Создан автор id = %d", author.getId());
    }

    @ShellMethod(value = "Get author by id", key = {"getauthor", "ga"})
    public String getAuthorById(@ShellOption long authorId) {
        try {
            Author author = repositoryJpa.findById(authorId).orElseThrow(() -> new NoAuthorFoundException(new Throwable()));
            return String.format("Автор: id = %d, name = %s", author.getId(), author.getName());
        } catch (NoAuthorFoundException e) {
            return String.format(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
        }
    }

    @ShellMethod(value = "Update author", key = {"updateauthor", "ua"})
    public String updateAuthor(@ShellOption long authorId, String authorName) {
        repositoryJpa.save(new Author(authorId, authorName));
        try {
            return String.format("Автор: id = %d, name = %s", authorId, authorName);
        } catch (NoAuthorFoundException e) {
            return String.format(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
        }
    }

    @ShellMethod(value = "Delete author", key = {"deleteauthor", "da"})
    public String deleteAuthorById(@ShellOption long authorId) {
        try {
            Author author = repositoryJpa.findById(authorId).orElseThrow(() -> new NoAuthorFoundException(new Throwable()));
            repositoryJpa.delete(author);
            return String.format("Удален автор id = %d", authorId);
        } catch (NoAuthorFoundException e) {
            return String.format(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
        }
    }

    @ShellMethod(value = "Count author", key = {"countauthor", "ca"})
    public String countAuthor() {
        return String.format("Общее количество авторов: %d", repositoryJpa.count());
    }

    @ShellMethod(value = "Get all authors", key = {"getallauthor", "gaa"})
    public void getAllAuthor() {
        for (Author author :
                repositoryJpa.findAll()) {
            System.out.println(author.getId() + " | " + author.getName());
        }
    }

    @ShellMethod(value = "Get author by book id", key = {"getbookauthor", "gba"})
    public void getBookAuthor(@ShellOption long bookId) {
        try {
            Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
            System.out.printf("Авторы книги \"%s\":", book.getName());
            System.out.println();
            for (Author author :
                    repositoryJpa.getAuthorsByBooks(book)) {
                System.out.println(author.getId() + " | " + author.getName());
            }
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        }
    }

}
