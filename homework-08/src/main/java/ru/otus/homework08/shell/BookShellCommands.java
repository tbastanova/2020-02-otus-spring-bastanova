package ru.otus.homework08.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework08.exception.NoAuthorFoundException;
import ru.otus.homework08.exception.NoBookFoundException;
import ru.otus.homework08.exception.NoCategoryFoundException;
import ru.otus.homework08.exception.NoCommentFoundException;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.service.BookService;
import ru.otus.homework08.service.CommentService;

@ShellComponent
public class BookShellCommands {
    private final BookService bookService;
    private final CommentService commentService;

    private static final String DUMMY_ID = "0";
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %s не найдена";
    private static final String NO_AUTHOR_FOUND_EXCEPTION_TEXT = "Автор с id = %s не найден";
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %s не найдена";
    private static final String NO_COMMENT_FOUND_EXCEPTION_TEXT = "Комментарий с id = %s не найден";

    public BookShellCommands(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @ShellMethod(value = "Insert book", key = {"insertbook", "ib"})
    public String insertBook(@ShellOption(defaultValue = "New Book") String bookName) {
        Book book = bookService.save(new Book(bookName));
        return String.format("Создана книга id = %s", book.getId());
    }

    @ShellMethod(value = "Get book by id", key = {"getbook", "gb"})
    public String getBookById(@ShellOption String bookId) {
        try {
            return bookService.getBookByIdToString(bookId);
        } catch (NoBookFoundException e) {
            return String.format(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
        }
    }

    @ShellMethod(value = "Update book", key = {"updatebook", "ub"})
    public String updateBook(@ShellOption String bookId, String bookName) {
        bookService.save(new Book(bookId, bookName));
        try {
            return bookService.getBookByIdToString(bookId);
        } catch (NoBookFoundException e) {
            return String.format(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
        }
    }

    @ShellMethod(value = "Delete book", key = {"deletebook", "db"})
    public String deleteBookById(@ShellOption String bookId) {
        try {
            bookService.deleteById(bookId);
            return String.format("Удалена книга id = %s", bookId);
        } catch (NoBookFoundException e) {
            return String.format(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
        }
    }

    @ShellMethod(value = "Count book", key = {"countbook", "cb"})
    public String countBook() {
        return String.format("Общее количество книг: %d", bookService.count());
    }

    @ShellMethod(value = "Get all books", key = {"getallbook", "gab", "getall", "all"})
    public void getAllBook() {
        System.out.println(bookService.getAllBooksToString());
    }

    @ShellMethod(value = "Set book author", key = {"setbookauthor", "sba"})
    public void setBookAuthor(@ShellOption String bookId, String authorId) {
        try {

            bookService.setBookAuthor(bookId, authorId);
            System.out.println("В книгу добавлен автор:");
            System.out.println(bookService.getBookByIdToString(bookId));
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        } catch (NoAuthorFoundException e) {
            System.out.printf(NO_AUTHOR_FOUND_EXCEPTION_TEXT, authorId);
            System.out.println();
        }
    }

    @ShellMethod(value = "Set book category", key = {"setbookcategory", "sbc"})
    public void setBookCategory(@ShellOption String bookId, String categoryId) {
        try {
            bookService.setBookCategory(bookId, categoryId);
            System.out.println("В книгу добавлена категория:");
            System.out.println(bookService.getBookByIdToString(bookId));
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        } catch (NoCategoryFoundException e) {
            System.out.printf(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
            System.out.println();
        }
    }

    @ShellMethod(value = "Get comments by book id", key = {"getbookcomment", "gbk"})
    public void getCommentByBookId(String bookId) {
        try {
            Book book = bookService.findById(bookId);
            System.out.print(bookService.getBookCommentToString(book, commentService.findByBook_Id(bookId)));
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        }
    }

    @ShellMethod(value = "Add book comment", key = {"addbookcomment", "abk"})
    public void setBookComment(@ShellOption String bookId, String commentText) {
        String commentId = "0";
        try {
            Comment comment = commentService.addBookComment(bookService.findById(bookId), commentText);
            commentId = comment.getId();
            System.out.printf("Добавлен комментарий id = %s \r\n", commentId);
        } catch (NoCommentFoundException e) {
            System.out.printf(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
            System.out.println();
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        }
    }
}
