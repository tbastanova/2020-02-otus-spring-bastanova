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
import ru.otus.homework06.exception.NoBookFoundException;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpaImpl.class)
class BookRepositoryJpaImplTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 10;
    private static final long FIRST_BOOK_ID = 1L;
    private static final String CURRENT_BOOK = "Current Book";
    private static final long DUMMY_ID = 0;
    private static final String DUMMY_BOOK = "Dummy Book";
    private static final String CHANGED_BOOK = "Changed Book";
    private static final long TEST_AUTHOR_ID_9 = 9;
    private static final long TEST_AUTHOR_ID_10 = 10;
    private static final long TEST_CATEGORY_ID_9 = 9;
    private static final long TEST_CATEGORY_ID_10 = 10;

    private static final int EXPECTED_QUERIES_COUNT = 5;

    @Autowired
    private BookRepositoryJpaImpl repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной книге по ее id")
    @Test
    void findById() {
        val optionalActualBook = repositoryJpa.findById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void findAll() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val books = repositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0)
                .allMatch(s -> s.getCategories() != null && s.getCategories().size() > 0);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("insert отработал корректно")
    @Test
    void insert() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        val optionalActualBook = repositoryJpa.findById(bookId);
        val expectedBook = em.find(Book.class, bookId);
        assertThat(optionalActualBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("update отработал корректно")
    @Test
    void update() {
        val firstBook = em.find(Book.class, FIRST_BOOK_ID);
        String oldName = firstBook.getName();
        em.detach(firstBook);

        repositoryJpa.update(new Book(FIRST_BOOK_ID, CHANGED_BOOK));
        val updatedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(updatedBook.getName()).isNotEqualTo(oldName).isEqualTo(CHANGED_BOOK);
    }

    @DisplayName("update не нашел книгу и вернул ошибку")
    @Test
    void updateNoRecordFound() {
        assertThrows(NoBookFoundException.class, () -> repositoryJpa.update(new Book(DUMMY_ID, DUMMY_BOOK)));
    }

    @DisplayName("count вернул корректно")
    @Test
    void count() {
        assertEquals(EXPECTED_NUMBER_OF_BOOKS, repositoryJpa.count());
    }

    @DisplayName("getById не нашел несуществующую книгу")
    @Test
    void getByIdNoFound() {
        assertEquals(Optional.empty(), repositoryJpa.findById(DUMMY_ID));
    }

    @DisplayName("getAll вернул пустой список при отсутствии книг")
    @Test
    void getAllExpectedNullWhenNoRecords() {
        List<Book> bookList = repositoryJpa.findAll();
        for (Book book :
                bookList) {
            repositoryJpa.deleteById(book.getId());
        }

        assertEquals(new ArrayList<>(), repositoryJpa.findAll());
    }

    @DisplayName("deleteById удалил запись")
    @Test
    void deleteById() {
        val firstBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        repositoryJpa.deleteById(FIRST_BOOK_ID);
        val deletedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }

    @DisplayName("setBookAuthor не нашел книгу")
    @Test
    void setBookAuthorNoBookFound() {

        assertThrows(NoBookFoundException.class, () -> repositoryJpa.addBookAuthor(DUMMY_ID, TEST_AUTHOR_ID_10));
    }

    @DisplayName("setBookAuthor не нашел автора")
    @Test
    void setBookAuthorNoAuthorFound() {
        assertThrows(NoAuthorFoundException.class, () -> repositoryJpa.addBookAuthor(FIRST_BOOK_ID, DUMMY_ID));
    }

    @DisplayName("setBookAuthor добавил автора")
    @Test
    void setBookAuthorAddNewAuthor() {
        assertFalse(repositoryJpa.getBookAuthor(FIRST_BOOK_ID).contains(em.find(Author.class, TEST_AUTHOR_ID_10)));

        repositoryJpa.addBookAuthor(FIRST_BOOK_ID, TEST_AUTHOR_ID_10);
        assertTrue(repositoryJpa.getBookAuthor(FIRST_BOOK_ID).contains(em.find(Author.class, TEST_AUTHOR_ID_10)));
    }

    @DisplayName("setBookAuthor проверил, что автор уже добавлен и не стал добавлять его 2й раз")
    @Test
    void setBookAuthorAuthorExists() {
        List<Author> authors = repositoryJpa.getBookAuthor(FIRST_BOOK_ID);
        assertFalse(authors.contains(em.find(Author.class, TEST_AUTHOR_ID_10)));
        int authorCount = authors.size();

        repositoryJpa.addBookAuthor(FIRST_BOOK_ID, TEST_AUTHOR_ID_10);
        repositoryJpa.addBookAuthor(FIRST_BOOK_ID, TEST_AUTHOR_ID_10);
        assertEquals(authorCount + 1, repositoryJpa.getBookAuthor(FIRST_BOOK_ID).size());
    }

    @DisplayName("getBookAuthor вернул автора")
    @Test
    void getBookAuthor() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        repositoryJpa.addBookAuthor(bookId, TEST_AUTHOR_ID_10);
        List<Author> authorList = repositoryJpa.getBookAuthor(bookId);
        assertTrue(authorList.size() == 1 && authorList.contains(em.find(Author.class, TEST_AUTHOR_ID_10)));
    }

    @DisplayName("getBookAuthor вернул двух авторов")
    @Test
    void getBookAuthors() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        repositoryJpa.addBookAuthor(bookId, TEST_AUTHOR_ID_9);
        repositoryJpa.addBookAuthor(bookId, TEST_AUTHOR_ID_10);
        List<Author> authorList = repositoryJpa.getBookAuthor(bookId);
        assertTrue(authorList.size() == 2 && authorList.contains(em.find(Author.class, TEST_AUTHOR_ID_9)) && authorList.contains(em.find(Author.class, TEST_AUTHOR_ID_10)));
    }

    @DisplayName("getBookAuthor вернул пустой список при отсутствии авторов")
    @Test
    void getBookAuthorNoAuthorFound() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        assertEquals(new ArrayList<>(), repositoryJpa.getBookAuthor(bookId));
    }

    @DisplayName("setBookCategory не нашел книгу")
    @Test
    void setBookCategoryNoBookFound() {
        assertThrows(NoBookFoundException.class, () -> repositoryJpa.addBookAuthor(DUMMY_ID, TEST_CATEGORY_ID_10));
    }

    @DisplayName("setBookCategory не нашел категорию")
    @Test
    void setBookCategoryNoCategoryFound() {
        assertThrows(NoCategoryFoundException.class, () -> repositoryJpa.addBookCategory(FIRST_BOOK_ID, DUMMY_ID));
    }

    @DisplayName("setBookCategory добавил категорию")
    @Test
    void setBookCategoryAddNewCategory() {
        assertFalse(repositoryJpa.getBookCategory(FIRST_BOOK_ID).contains(em.find(Category.class, TEST_CATEGORY_ID_10)));

        repositoryJpa.addBookCategory(FIRST_BOOK_ID, TEST_CATEGORY_ID_10);
        assertTrue(repositoryJpa.getBookCategory(FIRST_BOOK_ID).contains(em.find(Category.class, TEST_CATEGORY_ID_10)));
    }

    @DisplayName("setBookCategory проверил, что категория уже добавлена и не стал добавлять ее 2й раз")
    @Test
    void setBookCategoryCategoryExists() {
        List<Category> categories = repositoryJpa.getBookCategory(FIRST_BOOK_ID);
        assertFalse(categories.contains(em.find(Category.class, TEST_CATEGORY_ID_10)));
        int categoryCount = categories.size();

        repositoryJpa.addBookCategory(FIRST_BOOK_ID, TEST_CATEGORY_ID_10);
        repositoryJpa.addBookCategory(FIRST_BOOK_ID, TEST_CATEGORY_ID_10);
        assertEquals(categoryCount + 1, repositoryJpa.getBookCategory(FIRST_BOOK_ID).size());
    }

    @DisplayName("getBookCategory вернул категорию")
    @Test
    void getBookCategory() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        repositoryJpa.addBookCategory(bookId, TEST_CATEGORY_ID_10);
        List<Category> categoryList = repositoryJpa.getBookCategory(bookId);
        assertTrue(categoryList.size() == 1 && categoryList.contains(em.find(Category.class, TEST_CATEGORY_ID_10)));
    }

    @DisplayName("getBookCategory вернул две категории")
    @Test
    void getBookCategorys() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        repositoryJpa.addBookCategory(bookId, TEST_CATEGORY_ID_9);
        repositoryJpa.addBookCategory(bookId, TEST_CATEGORY_ID_10);
        List<Category> categoryList = repositoryJpa.getBookCategory(bookId);
        assertTrue(categoryList.size() == 2 && categoryList.contains(em.find(Category.class, TEST_CATEGORY_ID_9)) && categoryList.contains(em.find(Category.class, TEST_CATEGORY_ID_10)));
    }

    @DisplayName("getBookCategory вернул пустой список при отсутствии категорий")
    @Test
    void getBookCategoryNoCategoryFound() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        assertEquals(new ArrayList<>(), repositoryJpa.getBookCategory(bookId));
    }

    @DisplayName("checkExists проверил, что запись существует и вернул true")
    @Test
    void checkExistsReturnTrue() {
        long bookId = repositoryJpa.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        assertTrue(repositoryJpa.checkExists(bookId) && !repositoryJpa.findById(bookId).isEmpty());
    }

    @DisplayName("checkExists проверил, что запись не существует и вернул false")
    @Test
    void checkExistsReturnFalse() {
        assertTrue(!repositoryJpa.checkExists(DUMMY_ID) && repositoryJpa.findById(DUMMY_ID).isEmpty());
    }
}