package ru.otus.homework05.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.dao.CategoryDao;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.exception.NoCategoryFoundException;

@ShellComponent
public class CategoryShellCommands {
    private final CategoryDao categoryDao;

    private static final long DUMMY_ID = -1;
    private static final String NO_CATEGORY_FOUND_EXCEPTION_TEXT = "Категория с id = %d не найдена";

    public CategoryShellCommands(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @ShellMethod(value = "Insert category", key = {"insertcategory", "ic"})
    public String insertCategory(@ShellOption(defaultValue = "New Category") String categoryName) {
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, categoryName));
        return String.format("Создана категория id = %d", categoryId);
    }

    @ShellMethod(value = "Get category by id", key = {"getcategory", "gc"})
    public String getCategoryById(@ShellOption long categoryId) {
        Category category = categoryDao.getById(categoryId);
        return String.format("Категория: id = %d, name = %s", category.getId(), category.getName());
    }

    @ShellMethod(value = "Update category", key = {"updatecategory", "uc"})
    public String updateCategory(@ShellOption long categoryId, String categoryName) {
        categoryDao.update(new Category(categoryId, categoryName));
        try {
            return String.format("Категория: id = %d, name = %s", categoryId, categoryName);
        } catch (NoCategoryFoundException e) {
            return String.format(NO_CATEGORY_FOUND_EXCEPTION_TEXT, categoryId);
        }
    }

    @ShellMethod(value = "Delete category", key = {"deletecategory", "dc"})
    public String deleteCategoryById(@ShellOption long categoryId) {
        categoryDao.deleteById(categoryId);
        return String.format("Удалена категория id = %d", categoryId);
    }

    @ShellMethod(value = "Count category", key = {"countcategory", "cc"})
    public String countCategory() {
        return String.format("Общее количество категорий: %d", categoryDao.count());
    }

    @ShellMethod(value = "Get all categories", key = {"getallcategory", "gac"})
    public void getAllCategory() {
        for (Category category :
                categoryDao.getAll()) {
            System.out.println(category.getId() + " | " + category.getName());
        }
    }
}
