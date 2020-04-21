package ru.otus.homework06.repository.impl;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.exception.NoAuthorFoundException;
import ru.otus.homework06.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import({AuthorRepositoryJpaImpl.class})
class AuthorRepositoryJpaImplTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 11;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String CURRENT_AUTHOR = "Current Author";
    private static final long DUMMY_ID = 0;
    private static final String DUMMY_AUTHOR = "Dummy Author";
    private static final String CHANGED_AUTHOR = "Changed Author";

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private AuthorRepositoryJpaImpl repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Test
    void findById() {
        val optionalActualAuthor = repositoryJpa.findById(FIRST_AUTHOR_ID);
        val expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void findAll() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val authors = repositoryJpa.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(s -> !s.getName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("insert отработал корректно")
    @Test
    void insert() {
        long authorId = repositoryJpa.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        val optionalActualAuthor = repositoryJpa.findById(authorId);
        val expectedAuthor = em.find(Author.class, authorId);
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("update отработал корректно")
    @Test
    void update() {
        val firstAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        String oldName = firstAuthor.getName();
        em.detach(firstAuthor);

        repositoryJpa.update(new Author(FIRST_AUTHOR_ID, CHANGED_AUTHOR));
        val updatedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(updatedAuthor.getName()).isNotEqualTo(oldName).isEqualTo(CHANGED_AUTHOR);
    }

    @DisplayName("update не нашел автора и вернул ошибку")
    @Test
    void updateNoRecordFound() {
        assertThrows(NoAuthorFoundException.class, () -> repositoryJpa.update(new Author(DUMMY_ID, DUMMY_AUTHOR)));
    }

    @DisplayName("count вернул корректно")
    @Test
    void count() {
        assertEquals(EXPECTED_NUMBER_OF_AUTHORS, repositoryJpa.count());
    }

    @DisplayName("getById не нашел несуществующего автора")
    @Test
    void getByIdNoFound() {
        assertEquals(Optional.empty(), repositoryJpa.findById(DUMMY_ID));
    }

    @DisplayName("getAll вернул пустой список при отсутствии авторов")
    @Test
    void getAllExpectedNullWhenNoRecords() {
        List<Author> authorList = repositoryJpa.findAll();
        for (Author author :
                authorList) {
            repositoryJpa.deleteById(author.getId());
        }

        assertEquals(new ArrayList<>(), repositoryJpa.findAll());
    }

    @DisplayName("deleteById удалил запись")
    @Test
    void deleteById() {
        val firstAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(firstAuthor).isNotNull();
        em.detach(firstAuthor);

        repositoryJpa.deleteById(FIRST_AUTHOR_ID);
        val deletedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(deletedAuthor).isNull();
    }

    @DisplayName("checkExists проверил, что запись существует и вернул true")
    @Test
    void checkExistsReturnTrue() {
        long authorId = repositoryJpa.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        assertTrue(repositoryJpa.checkExists(authorId) && !repositoryJpa.findById(authorId).isEmpty());
    }

    @DisplayName("checkExists проверил, что запись не существует и вернул false")
    @Test
    void checkExistsReturnFalse() {
        assertTrue(!repositoryJpa.checkExists(DUMMY_ID) && repositoryJpa.findById(DUMMY_ID).isEmpty());
    }
}