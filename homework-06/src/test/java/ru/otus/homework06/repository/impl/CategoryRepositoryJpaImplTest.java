package ru.otus.homework06.repository.impl;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с категориями ")
@DataJpaTest
@Import({CategoryRepositoryJpaImpl.class})
class CategoryRepositoryJpaImplTest {

    private static final int EXPECTED_NUMBER_OF_CATEGORIES = 11;
    private static final long FIRST_CATEGORY_ID = 1L;
    private static final String CURRENT_CATEGORY = "Current Category";
    private static final long DUMMY_ID = 0;
    private static final String DUMMY_CATEGORY = "Dummy Category";
    private static final String CHANGED_CATEGORY = "Changed Category";

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private CategoryRepositoryJpaImpl repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной категории по ее id")
    @Test
    void findById() {
        val optionalActualCategory = repositoryJpa.findById(FIRST_CATEGORY_ID);
        val expectedCategory = em.find(Category.class, FIRST_CATEGORY_ID);
        assertThat(optionalActualCategory).isPresent().get()
                .isEqualToComparingFieldByField(expectedCategory);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void findAll() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_CATEGORIES)
                .allMatch(s -> !s.getName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("insert отработал корректно")
    @Test
    void insert() {
        long categoryId = repositoryJpa.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        val optionalActualAuthor = repositoryJpa.findById(categoryId);
        val expectedAuthor = em.find(Category.class, categoryId);
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("update отработал корректно")
    @Test
    void update() {
        val firstCategory = em.find(Category.class, FIRST_CATEGORY_ID);
        String oldName = firstCategory.getName();
        em.detach(firstCategory);

        repositoryJpa.update(new Category(FIRST_CATEGORY_ID, CHANGED_CATEGORY));
        val updatedCategory = em.find(Category.class, FIRST_CATEGORY_ID);

        assertThat(updatedCategory.getName()).isNotEqualTo(oldName).isEqualTo(CHANGED_CATEGORY);
    }

    @DisplayName("update не нашел категорию и вернул ошибку")
    @Test
    void updateNoRecordFound() {
        assertThrows(NoCategoryFoundException.class, () -> repositoryJpa.update(new Category(DUMMY_ID, DUMMY_CATEGORY)));
    }

    @DisplayName("count вернул корректно")
    @Test
    void count() {
        assertEquals(EXPECTED_NUMBER_OF_CATEGORIES, repositoryJpa.count());
    }

    @DisplayName("getById не нашел несуществующую категорию")
    @Test
    void getByIdNoFound() {
        assertEquals(Optional.empty(), repositoryJpa.findById(DUMMY_ID));
    }

    @DisplayName("getAll вернул пустой список при отсутствии категорий")
    @Test
    void getAllExpectedNullWhenNoRecords() {
        List<Category> categoryList = repositoryJpa.findAll();
        for (Category category :
                categoryList) {
            repositoryJpa.deleteById(category.getId());
        }

        assertEquals(new ArrayList<>(), repositoryJpa.findAll());
    }

    @DisplayName("deleteById удалил запись")
    @Test
    void deleteById() {
        val firstCategory = em.find(Category.class, FIRST_CATEGORY_ID);
        assertThat(firstCategory).isNotNull();
        em.detach(firstCategory);

        repositoryJpa.deleteById(FIRST_CATEGORY_ID);
        val deletedCategory = em.find(Category.class, FIRST_CATEGORY_ID);

        assertThat(deletedCategory).isNull();
    }

    @DisplayName("checkExists проверил, что запись существует и вернул true")
    @Test
    void checkExistsReturnTrue() {
        long categoryId = repositoryJpa.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        assertTrue(repositoryJpa.checkExists(categoryId) && !repositoryJpa.findById(categoryId).isEmpty());
    }

    @DisplayName("checkExists проверил, что запись не существует и вернул false")
    @Test
    void checkExistsReturnFalse() {
        assertTrue(!repositoryJpa.checkExists(DUMMY_ID) && repositoryJpa.findById(DUMMY_ID).isEmpty());
    }
}