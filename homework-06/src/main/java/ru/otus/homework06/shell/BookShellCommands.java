package ru.otus.homework06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework06.exception.NoAuthorFoundException;
import ru.otus.homework06.exception.NoBookFoundException;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.exception.NoCommentFoundException;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Category;
import ru.otus.homework06.repository.BookRepositoryJpa;
import ru.otus.homework06.service.BookService;
import ru.otus.homework06.service.CommentService;

@ShellComponent
public class BookShellCommands {
    private final BookRepositoryJpa repositoryJpa;
    private final BookService bookService;
    private final CommentService commentService;

    private static final long DUMMY_ID = -1;
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %d не найдена";
    private static final String NO_AUTHOR_FOUND_EXCEPTION_TEXT = "Автор с id = %d не найден";
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %d не найдена";
    private static final String NO_COMMENT_FOUND_EXCEPTION_TEXT = "Комментарий с id = %d не найден";

    public BookShellCommands(BookRepositoryJpa repositoryJpa, BookService bookService, CommentService commentService) {
        this.repositoryJpa = repositoryJpa;
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @ShellMethod(value = "Insert book", key = {"insertbook", "ib"})
    public String insertBook(@ShellOption(defaultValue = "New Book") String bookName) {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, bookName));
        return String.format("Создана книга id = %d", bookId);
    }

    @ShellMethod(value = "Get book by id", key = {"getbook", "gb"})
    public String getBookById(@ShellOption long bookId) {
        Book book = repositoryJpa.findById(bookId).get();
        return bookService.getBookToString(book);
    }

    @ShellMethod(value = "Update book", key = {"updatebook", "ub"})
    public String updateBook(@ShellOption long bookId, String bookName) {
        repositoryJpa.update(new Book(bookId, bookName));
        try {
            return bookService.getBookToString(repositoryJpa.findById(bookId).get());
        } catch (NoBookFoundException e) {
            return String.format(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
        }
    }

    @ShellMethod(value = "Delete book", key = {"deletebook", "db"})
    public String deleteBookById(@ShellOption long bookId) {
        repositoryJpa.deleteById(bookId);
        return String.format("Удалена книга id = %d", bookId);
    }

    @ShellMethod(value = "Count book", key = {"countbook", "cb"})
    public String countBook() {
        return String.format("Общее количество книг: %d", repositoryJpa.count());
    }

    @ShellMethod(value = "Get all books", key = {"getallbook", "gab", "getall", "all"})
    public void getAllBook() {
        for (Book book :
                repositoryJpa.findAll()) {
            System.out.println(bookService.getBookToString(book));
        }
    }

    @ShellMethod(value = "Set book author", key = {"setbookauthor", "sba"})
    public void setBookAuthor(@ShellOption long bookId, long authorId) {
        try {
            repositoryJpa.addBookAuthor(bookId, authorId);
            System.out.println("В книгу добавлен автор:");
            System.out.println(bookService.getBookToString(repositoryJpa.findById(bookId).get()));
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        } catch (NoAuthorFoundException e) {
            System.out.printf(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
            System.out.println();
        }
    }

    @ShellMethod(value = "Get book author", key = {"getbookauthor", "gba"})
    public void getBookAuthor(@ShellOption long bookId) {
        System.out.printf("Авторы книги \"%s\":", repositoryJpa.findById(bookId).get().getName());
        System.out.println();
        for (Author author :
                repositoryJpa.getBookAuthor(bookId)) {
            System.out.println(author.getId() + " | " + author.getName());
        }
    }

    @ShellMethod(value = "Set book category", key = {"setbookcategory", "sbc"})
    public void setBookCategory(@ShellOption long bookId, long categoryId) {
        try {
            repositoryJpa.addBookCategory(bookId, categoryId);
            System.out.println("В книгу добавлена категория:");
            System.out.println(bookService.getBookToString(repositoryJpa.findById(bookId).get()));
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        } catch (NoCategoryFoundException e) {
            System.out.printf(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
            System.out.println();
        }
    }

    @ShellMethod(value = "Get book category", key = {"getbookcategory", "gbc"})
    public void getBookCategory(@ShellOption long bookId) {
        System.out.printf("Категории книги \"%s\":", repositoryJpa.findById(bookId).get().getName());
        System.out.println();
        for (Category category :
                repositoryJpa.getBookCategory(bookId)) {
            System.out.println(category.getId() + " | " + category.getName());
        }
    }

    @ShellMethod(value = "Get comments by book id", key = {"getbookcomment", "gbk"})
    public void getCommentByBookId(long bookId) {
        System.out.print(bookService.getBookCommentToString(bookId));
    }

    @ShellMethod(value = "Add book comment", key = {"addbookcomment", "abk"})
    public void setBookComment(@ShellOption long bookId, String commentText) {
        long commentId = 0;
        try {
            commentId = commentService.addBookComment(bookId, commentText);
            System.out.printf("Добавлен комментарий id = %d \r\n", commentId);
        } catch (NoCommentFoundException e) {
            System.out.printf(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
            System.out.println();
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        }
    }
}
