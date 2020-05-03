package ru.otus.homework07.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework07.exception.NoBookFoundException;
import ru.otus.homework07.exception.NoCategoryFoundException;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Category;
import ru.otus.homework07.repository.BookRepositoryJpa;
import ru.otus.homework07.repository.CategoryRepositoryJpa;

@ShellComponent
public class CategoryShellCommands {
    private final CategoryRepositoryJpa repositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    private static final long DUMMY_ID = -1;
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %d не найдена";
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %d не найдена";

    public CategoryShellCommands(CategoryRepositoryJpa repositoryJpa, BookRepositoryJpa bookRepositoryJpa) {
        this.repositoryJpa = repositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @ShellMethod(value = "Insert category", key = {"insertcategory", "ic"})
    public String insertCategory(@ShellOption(defaultValue = "New Category") String categoryName) {
        Category category = repositoryJpa.save(new Category(DUMMY_ID, categoryName));
        return String.format("Создана категория id = %d", category.getId());
    }

    @ShellMethod(value = "Get category by id", key = {"getcategory", "gc"})
    public String getCategoryById(@ShellOption long categoryId) {
        try {
            Category category = repositoryJpa.findById(categoryId).orElseThrow(() -> new NoCategoryFoundException(new Throwable()));
            return String.format("Категория: id = %d, name = %s", category.getId(), category.getName());
        } catch (NoCategoryFoundException e) {
            return String.format(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
        }
    }

    @ShellMethod(value = "Update category", key = {"updatecategory", "uc"})
    public String updateCategory(@ShellOption long categoryId, String categoryName) {
        repositoryJpa.save(new Category(categoryId, categoryName));
        try {
            return String.format("Категория: id = %d, name = %s", categoryId, categoryName);
        } catch (NoCategoryFoundException e) {
            return String.format(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
        }
    }

    @ShellMethod(value = "Delete category", key = {"deletecategory", "dc"})
    public String deleteCategoryById(@ShellOption long categoryId) {
        repositoryJpa.deleteById(categoryId);
        return String.format("Удалена категория id = %d", categoryId);
    }

    @ShellMethod(value = "Count category", key = {"countcategory", "cc"})
    public String countCategory() {
        return String.format("Общее количество категорий: %d", repositoryJpa.count());
    }

    @ShellMethod(value = "Get all categories", key = {"getallcategory", "gac"})
    public void getAllCategory() {
        for (Category category :
                repositoryJpa.findAll()) {
            System.out.println(category.getId() + " | " + category.getName());
        }
    }

    @ShellMethod(value = "Get category by book id", key = {"getbookcategory", "gbc"})
    public void getBookCategory(@ShellOption long bookId) {
        try {
            Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
            System.out.printf("Категории книги \"%s\":", book.getName());
            System.out.println();
            for (Category category :
                    repositoryJpa.getCategoriesByBooks(book)) {
                System.out.println(category.getId() + " | " + category.getName());
            }
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        }
    }
}
