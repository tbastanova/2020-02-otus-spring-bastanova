package ru.otus.homework06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.model.Category;
import ru.otus.homework06.repository.BookRepositoryJpa;
import ru.otus.homework06.repository.CategoryRepositoryJpa;

@ShellComponent
public class CategoryShellCommands {
    private final CategoryRepositoryJpa repositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    private static final long DUMMY_ID = -1;
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %d не найдена";

    public CategoryShellCommands(CategoryRepositoryJpa repositoryJpa, BookRepositoryJpa bookRepositoryJpa) {
        this.repositoryJpa = repositoryJpa;
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @ShellMethod(value = "Insert category", key = {"insertcategory", "ic"})
    @Transactional
    public String insertCategory(@ShellOption(defaultValue = "New Category") String categoryName) {
        long categoryId = repositoryJpa.insert(new Category(DUMMY_ID, categoryName));
        return String.format("Создана категория id = %d", categoryId);
    }

    @ShellMethod(value = "Get category by id", key = {"getcategory", "gc"})
    @Transactional(readOnly = true)
    public String getCategoryById(@ShellOption long categoryId) {
        Category category = repositoryJpa.findById(categoryId).get();
        return String.format("Категория: id = %d, name = %s", category.getId(), category.getName());
    }

    @ShellMethod(value = "Update category", key = {"updatecategory", "uc"})
    @Transactional
    public String updateCategory(@ShellOption long categoryId, String categoryName) {
        repositoryJpa.update(new Category(categoryId, categoryName));
        try {
            return String.format("Категория: id = %d, name = %s", categoryId, categoryName);
        } catch (NoCategoryFoundException e) {
            return String.format(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
        }
    }

    @ShellMethod(value = "Delete category", key = {"deletecategory", "dc"})
    @Transactional
    public String deleteCategoryById(@ShellOption long categoryId) {
        repositoryJpa.deleteById(categoryId);
        return String.format("Удалена категория id = %d", categoryId);
    }

    @ShellMethod(value = "Count category", key = {"countcategory", "cc"})
    public String countCategory() {
        return String.format("Общее количество категорий: %d", repositoryJpa.count());
    }

    @ShellMethod(value = "Get all categories", key = {"getallcategory", "gac"})
    @Transactional(readOnly = true)
    public void getAllCategory() {
        for (Category category :
                repositoryJpa.findAll()) {
            System.out.println(category.getId() + " | " + category.getName());
        }
    }

    @ShellMethod(value = "Get category by book id", key = {"getbookcategory", "gbc"})
    @Transactional(readOnly = true)
    public void getBookCategory(@ShellOption long bookId) {
        System.out.printf("Категории книги \"%s\":", bookRepositoryJpa.findById(bookId).get().getName());
        System.out.println();
        for (Category category :
                repositoryJpa.getCategoriesByBookId(bookId)) {
            System.out.println(category.getId() + " | " + category.getName());
        }
    }
}
