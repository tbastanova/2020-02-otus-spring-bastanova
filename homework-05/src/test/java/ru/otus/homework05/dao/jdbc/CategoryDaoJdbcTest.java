package ru.otus.homework05.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework05.dao.CategoryDao;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.exception.NoCategoryFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс CategoryDaoJdbcTest")
@JdbcTest
@Import({CategoryDaoJdbc.class})
class CategoryDaoJdbcTest {

    @Autowired
    private CategoryDao categoryDao;

    private static final String TEST_CATEGORY_1 = "Test Category1";
    private static final long TEST_ID_1 = 1;
    private static final String TEST_CATEGORY_2 = "Test Category2";
    private static final long TEST_ID_2 = 2;
    private static final int EXPECTED_COUNT = 2;
    private static final String CURRENT_CATEGORY = "Current Category";
    private static final long DUMMY_ID = -1;
    private static final String DUMMY_CATEGORY = "Dummy Category";
    private static final String CHANGED_CATEGORY = "Changed Category";

    @DisplayName("insert отработал корректно")
    @Test
    void insert() {
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        assertEquals(CURRENT_CATEGORY, categoryDao.getById(categoryId).getName());
    }

    @DisplayName("update отработал корректно")
    @Test
    void update() {
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, DUMMY_CATEGORY));
        Category category = new Category(categoryId, CHANGED_CATEGORY);
        categoryDao.update(category);
        assertEquals(category, categoryDao.getById(categoryId));
    }

    @DisplayName("update не нашел категорию и вернул ошибку")
    @Test
    void updateNoRecordFound() {
        assertThrows(NoCategoryFoundException.class, () -> categoryDao.update(new Category(DUMMY_ID, DUMMY_CATEGORY)));
    }

    @DisplayName("count вернул корректно")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void count() {
        assertEquals(EXPECTED_COUNT, categoryDao.count());
    }

    @DisplayName("getById нашел категорию")
    @Test
    void getById() {
        assertEquals(new Category(TEST_ID_2, TEST_CATEGORY_2), categoryDao.getById(TEST_ID_2));
    }

    @DisplayName("getById не нашел несуществующую категорию")
    @Test
    void getByIdNoFound() {
        assertNull(categoryDao.getById(DUMMY_ID));
    }

    @DisplayName("getAll вернул все категории")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getAll() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(TEST_ID_1, TEST_CATEGORY_1));
        categoryList.add(new Category(TEST_ID_2, TEST_CATEGORY_2));

        assertEquals(categoryList, categoryDao.getAll());
    }

    @DisplayName("getAll вернул пустой список при отсутствии категорий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllExpectedNullWhenNoRecords() {
        List<Category> categoryList = categoryDao.getAll();
        for (Category category :
                categoryList) {
            categoryDao.deleteById(category.getId());
        }
        assertEquals(new ArrayList<>(), categoryDao.getAll());
    }

    @DisplayName("deleteById удалил запись")
    @Test
    void deleteById() {
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, DUMMY_CATEGORY));
        categoryDao.deleteById(categoryId);
        assertNull(categoryDao.getById(categoryId));
    }

    @DisplayName("checkExists проверил, что запись существует и вернул true")
    @Test
    void checkExistsReturnTrue() {
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, DUMMY_CATEGORY));
        assertTrue(categoryDao.checkExists(categoryId) && categoryDao.getById(categoryId) != null);
    }

    @DisplayName("checkExists проверил, что запись существует и вернул false")
    @Test
    void checkExistsReturnFalse() {
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, DUMMY_CATEGORY));
        categoryDao.deleteById(categoryId);
        assertTrue(!categoryDao.checkExists(categoryId) && categoryDao.getById(categoryId) == null);
    }
}