package ru.otus.homework05.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.exception.NoAuthorFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс AuthorDaoJdbcTest")
@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDao;

    private static final String TEST_AUTHOR_1 = "Test Author1";
    private static final long TEST_ID_1 = 1;
    private static final String TEST_AUTHOR_2 = "Test Author2";
    private static final long TEST_ID_2 = 2;
    private static final int EXPECTED_COUNT = 2;
    private static final String CURRENT_AUTHOR = "Current Author";
    private static final long DUMMY_ID = -1;
    private static final String DUMMY_AUTHOR = "Dummy Author";
    private static final String CHANGED_AUTHOR = "Changed Author";

    @DisplayName("insert отработал корректно")
    @Test
    void insert() {
        long authorId = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        assertEquals(CURRENT_AUTHOR, authorDao.getById(authorId).getName());
    }

    @DisplayName("update отработал корректно")
    @Test
    void update() {
        long authorId = authorDao.insert(new Author(DUMMY_ID, DUMMY_AUTHOR));
        Author author = new Author(authorId, CHANGED_AUTHOR);
        authorDao.update(author);
        assertEquals(author, authorDao.getById(authorId));
    }

    @DisplayName("update не нашел автора и вернул ошибку")
    @Test
    void updateNoRecordFound() {
        assertThrows(NoAuthorFoundException.class, () -> authorDao.update(new Author(DUMMY_ID, DUMMY_AUTHOR)));
    }

    @DisplayName("count вернул корректно")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void count() {
        assertEquals(EXPECTED_COUNT, authorDao.count());
    }

    @DisplayName("getById нашел автора")
    @Test
    void getById() {
        assertEquals(new Author(TEST_ID_2, TEST_AUTHOR_2), authorDao.getById(TEST_ID_2));
    }

    @DisplayName("getById не нашел несуществующего автора")
    @Test
    void getByIdNoFound() {
        assertNull(authorDao.getById(DUMMY_ID));
    }

    @DisplayName("getAll вернул всех авторов")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getAll() {
        List<Author> authorList = new ArrayList<>();
        authorList.add(new Author(TEST_ID_1, TEST_AUTHOR_1));
        authorList.add(new Author(TEST_ID_2, TEST_AUTHOR_2));

        assertEquals(authorList, authorDao.getAll());
    }

    @DisplayName("getAll вернул пустой список при отсутствии авторов")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllExpectedNullWhenNoRecords() {
        List<Author> authorList = authorDao.getAll();
        for (Author author :
                authorList) {
            authorDao.deleteById(author.getId());
        }
        assertEquals(new ArrayList<>(), authorDao.getAll());
    }

    @DisplayName("deleteById удалил запись")
    @Test
    void deleteById() {
        long authorId = authorDao.insert(new Author(DUMMY_ID, DUMMY_AUTHOR));
        authorDao.deleteById(authorId);
        assertNull(authorDao.getById(authorId));
    }
}