package ru.otus.homework05.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.dao.BookDao;
import ru.otus.homework05.dao.CategoryDao;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.exception.NoAuthorFoundException;
import ru.otus.homework05.exception.NoBookFoundException;
import ru.otus.homework05.exception.NoCategoryFoundException;

@ShellComponent
public class BookShellCommands {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;

    private static final long DUMMY_ID = -1;
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %d не найдена";
    private static final String NO_AUTHOR_FOUND_EXCEPTION_TEXT = "Автор с id = %d не найден";
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %d не найдена";

    public BookShellCommands(BookDao bookDao, AuthorDao authorDao, CategoryDao categoryDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.categoryDao = categoryDao;
    }

    @ShellMethod(value = "Insert book", key = {"insertbook", "ib"})
    public String insertBook(@ShellOption(defaultValue = "New Book") String bookName) {
        long bookId = bookDao.insert(new Book(DUMMY_ID, bookName));
        return String.format("Создана книга id = %d", bookId);
    }

    @ShellMethod(value = "Get book by id", key = {"getbook", "gb"})
    public String getBookById(@ShellOption long bookId) {
        Book book = bookDao.getById(bookId);
        return String.format("Книга: id = %d, name = %s", book.getId(), book.getName());
    }

    @ShellMethod(value = "Update book", key = {"updatebook", "ub"})
    public String updateBook(@ShellOption long bookId, String bookName) {
        bookDao.update(new Book(bookId, bookName));
        try {
            return String.format("Книга: id = %d, name = %s", bookId, bookName);
        } catch (NoBookFoundException e) {
            return String.format(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
        }
    }

    @ShellMethod(value = "Delete book", key = {"deletebook", "db"})
    public String deleteBookById(@ShellOption long bookId) {
        bookDao.deleteById(bookId);
        return String.format("Удалена книга id = %d", bookId);
    }

    @ShellMethod(value = "Count book", key = {"countbook", "cb"})
    public String countBook() {
        return String.format("Общее количество книг: %d", bookDao.count());
    }

    @ShellMethod(value = "Get all books", key = {"getallbook", "gab"})
    public void getAllBook() {
        for (Book book :
                bookDao.getAll()) {
            System.out.println(getBookToString(book.getId()));
        }
    }

    @ShellMethod(value = "Get all about books", key = {"getall", "all"})
    public void getAll() {
        for (Book book :
                bookDao.getAll()) {
            System.out.println(getBookToString(book.getId()) + " | " + getBookAutorToString(book.getId()) + " | " + getBookCategoryToString(book.getId()));
        }
    }

    @ShellMethod(value = "Set book author", key = {"setbookauthor", "sba"})
    public void setBookAuthor(@ShellOption long bookId, long authorId) {
        try {
            bookDao.setBookAuthor(bookId, authorId);
            System.out.println("В книгу добавлен автор:");
            System.out.println(getBookToString(bookId) + " | " + getBookAutorToString(bookId));
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
        System.out.printf("Авторы книги \"%s\":", bookDao.getById(bookId).getName());
        System.out.println();
        for (Author author :
                bookDao.getBookAuthor(bookId)) {
            System.out.println(author.getId() + " | " + author.getName());
        }
    }

    @ShellMethod(value = "Set book category", key = {"setbookcategory", "sbc"})
    public void setBookCategory(@ShellOption long bookId, long categoryId) {
        try {
            bookDao.setBookCategory(bookId, categoryId);
            System.out.println("В книгу добавлена категория:");
            System.out.println(getBookToString(bookId) + " | " + getBookCategoryToString(bookId));
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
        System.out.printf("Категории книги \"%s\":", bookDao.getById(bookId).getName());
        System.out.println();
        for (Category category :
                bookDao.getBookCategory(bookId)) {
            System.out.println(category.getId() + " | " + category.getName());
        }
    }

    private String getBookToString(long bookId) {
        Book book = bookDao.getById(bookId);
        return String.format("%d | %s", book.getId(), book.getName());
    }

    private String getBookAutorToString(long bookId) {
        String authors = new String();
        for (Author author :
                bookDao.getBookAuthor(bookId)) {
            authors = authors + author.getName() + "; ";
        }
        return authors;
    }

    private String getBookCategoryToString(long bookId) {
        String categories = new String();
        for (Category category :
                bookDao.getBookCategory(bookId)) {
            categories = categories + category.getName() + "; ";
        }
        return categories;
    }
}
