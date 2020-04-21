package ru.otus.homework06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.model.Category;
import ru.otus.homework06.repository.CategoryRepositoryJpa;

@ShellComponent
public class CategoryShellCommands {
    private final CategoryRepositoryJpa repositoryJpa;

    private static final long DUMMY_ID = -1;
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %d не найдена";

    public CategoryShellCommands(CategoryRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @ShellMethod(value = "Insert category", key = {"insertcategory", "ic"})
    public String insertCategory(@ShellOption(defaultValue = "New Category") String categoryName) {
        long categoryId = repositoryJpa.insert(new Category(DUMMY_ID, categoryName));
        return String.format("Создана категория id = %d", categoryId);
    }

    @ShellMethod(value = "Get category by id", key = {"getcategory", "gc"})
    public String getCategoryById(@ShellOption long categoryId) {
        Category category = repositoryJpa.findById(categoryId).get();
        return String.format("Категория: id = %d, name = %s", category.getId(), category.getName());
    }

    @ShellMethod(value = "Update category", key = {"updatecategory", "uc"})
    public String updateCategory(@ShellOption long categoryId, String categoryName) {
        repositoryJpa.update(new Category(categoryId, categoryName));
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
}
