package ru.otus.homework08.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework08.exception.NoBookFoundException;
import ru.otus.homework08.exception.NoCategoryFoundException;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Category;
import ru.otus.homework08.service.BookService;
import ru.otus.homework08.service.CategoryService;

@ShellComponent
public class CategoryShellCommands {
    private final CategoryService categoryService;
    private final BookService bookService;

    private static final String DUMMY_ID = "0";
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %s не найдена";
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %s не найдена";

    public CategoryShellCommands(CategoryService categoryService, BookService bookService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @ShellMethod(value = "Insert category", key = {"insertcategory", "ic"})
    public String insertCategory(@ShellOption(defaultValue = "New Category") String categoryName) {
        Category category = categoryService.save(new Category(categoryName));
        return String.format("Создана категория id = %s", category.getId());
    }

    @ShellMethod(value = "Get category by id", key = {"getcategory", "gc"})
    public String getCategoryById(@ShellOption String categoryId) {
        try {
            Category category = categoryService.findById(categoryId);
            return String.format("Категория: id = %s, name = %s", category.getId(), category.getName());
        } catch (NoCategoryFoundException e) {
            return String.format(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
        }
    }

    @ShellMethod(value = "Update category", key = {"updatecategory", "uc"})
    public String updateCategory(@ShellOption String categoryId, String categoryName) {
        categoryService.save(new Category(categoryId, categoryName));
        try {
            return String.format("Категория: id = %s, name = %s", categoryId, categoryName);
        } catch (NoCategoryFoundException e) {
            return String.format(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
        }
    }

    @ShellMethod(value = "Delete category", key = {"deletecategory", "dc"})
    public String deleteCategoryById(@ShellOption String categoryId) {
        categoryService.deleteById(categoryId);
        return String.format("Удалена категория id = %s", categoryId);
    }

    @ShellMethod(value = "Count category", key = {"countcategory", "cc"})
    public String countCategory() {
        return String.format("Общее количество категорий: %d", categoryService.count());
    }

    @ShellMethod(value = "Get all categories", key = {"getallcategory", "gac"})
    public void getAllCategory() {
        for (Category category :
                categoryService.findAll()) {
            System.out.println(category.getId() + " | " + category.getName());
        }
    }

    @ShellMethod(value = "Get category by book id", key = {"getbookcategory", "gbc"})
    public void getBookCategory(@ShellOption String bookId) {
        try {
            Book book = bookService.findById(bookId);
            System.out.printf("Категории книги \"%s\":", book.getName());
            System.out.println();
            for (Category category :
                    bookService.getBookCategoriesByBookId(bookId)) {
                System.out.println(category.getId() + " | " + category.getName());
            }
        } catch (NoBookFoundException e) {
            System.out.printf(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
            System.out.println();
        }
    }
}
