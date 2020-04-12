package ru.otus.homework05.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.exception.NoAuthorFoundException;

@ShellComponent
public class AuthorShellCommands {
    private final AuthorDao authorDao;

    private static final long DUMMY_ID = -1;
    private static final String NO_AUTHOR_FOUND_EXCEPTION_TEXT = "Автор с id = %d не найден";

    public AuthorShellCommands(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @ShellMethod(value = "Insert author", key = {"insertauthor", "ia"})
    public String insertAuthor(@ShellOption(defaultValue = "New Author") String authorName) {
        long authorId = authorDao.insert(new Author(DUMMY_ID, authorName));
        return String.format("Создан автор id = %d", authorId);
    }

    @ShellMethod(value = "Get author by id", key = {"getauthor", "ga"})
    public String getAuthorById(@ShellOption long authorId) {
        Author author = authorDao.getById(authorId);
        return String.format("Автор: id = %d, name = %s", author.getId(), author.getName());
    }

    @ShellMethod(value = "Update author", key = {"updateauthor", "ua"})
    public String updateAuthor(@ShellOption long authorId, String authorName) {
        authorDao.update(new Author(authorId, authorName));
        try {
            return String.format("Автор: id = %d, name = %s", authorId, authorName);
        } catch (NoAuthorFoundException e) {
            return String.format(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
        }
    }

    @ShellMethod(value = "Delete author", key = {"deleteauthor", "da"})
    public String deleteAuthorById(@ShellOption long authorId) {
        authorDao.deleteById(authorId);
        return String.format("Удален автор id = %d", authorId);
    }

    @ShellMethod(value = "Count author", key = {"countauthor", "ca"})
    public String countAuthor() {
        return String.format("Общее количество авторов: %d", authorDao.count());
    }

    @ShellMethod(value = "Get all authors", key = {"getallauthor", "gaa"})
    public void getAllAuthor() {
        for (Author author :
                authorDao.getAll()) {
            System.out.println(author.getId() + " | " + author.getName());
        }
    }
}
