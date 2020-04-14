package ru.otus.homework05.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.dao.BookDao;
import ru.otus.homework05.dao.CategoryDao;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.exception.NoAuthorFoundException;
import ru.otus.homework05.exception.NoBookFoundException;
import ru.otus.homework05.exception.NoCategoryFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс BookDaoJdbcTest")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, CategoryDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private CategoryDao categoryDao;

    private static final String TEST_BOOK_1 = "Test Book1";
    private static final long TEST_ID_1 = 1;
    private static final String TEST_BOOK_2 = "Test Book2";
    private static final long TEST_ID_2 = 2;
    private static final int EXPECTED_COUNT = 2;
    private static final String CURRENT_BOOK = "Current Book";
    private static final long DUMMY_ID = -1;
    private static final String DUMMY_BOOK = "Dummy Book";
    private static final String CHANGED_BOOK = "Changed Book";
    private static final long TEST_AUTHOR_ID_1 = 1;
    private static final String TEST_AUTHOR_1 = "Test Author1";
    private static final String CURRENT_AUTHOR = "Current Author";
    private static final String CURRENT_AUTHOR_2 = "Current Author2";
    private static final long TEST_CATEGORY_ID_1 = 1;
    private static final String TEST_CATEGORY_1 = "Test Category1";
    private static final String CURRENT_CATEGORY = "Current Category";
    private static final String CURRENT_CATEGORY_2 = "Current Category2";

    @DisplayName("insert отработал корректно")
    @Test
    void insert() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        assertEquals(CURRENT_BOOK, bookDao.getById(bookId).getName());
    }

    @DisplayName("update отработал корректно")
    @Test
    void update() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, DUMMY_BOOK));
        Book book = new Book(bookId, CHANGED_BOOK);
        bookDao.update(book);
        assertEquals(book, bookDao.getById(bookId));
    }

    @DisplayName("update не нашел книгу и вернул ошибку")
    @Test
    void updateNoRecordFound() {
        assertThrows(NoBookFoundException.class, () -> bookDao.update(new Book(DUMMY_ID, DUMMY_BOOK)));
    }

    @DisplayName("count вернул корректно")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void count() {
        assertEquals(EXPECTED_COUNT, bookDao.count());
    }

    @DisplayName("getById нашел книгу")
    @Test
    void getById() {
        assertEquals(new Book(TEST_ID_2, TEST_BOOK_2), bookDao.getById(TEST_ID_2));
    }

    @DisplayName("getById не нашел несуществующую книгу")
    @Test
    void getByIdNoFound() {
        assertNull(bookDao.getById(DUMMY_ID));
    }

    @DisplayName("getAll вернул все книги")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getAll() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(TEST_AUTHOR_ID_1, TEST_AUTHOR_1));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(TEST_CATEGORY_ID_1, TEST_CATEGORY_1));

        Book book = new Book(TEST_ID_1, TEST_BOOK_1);
        book.setAuthors(authors);
        book.setCategories(categories);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(new Book(TEST_ID_2, TEST_BOOK_2));

        assertEquals(bookList, bookDao.getAll());
    }

    @DisplayName("getAll вернул пустой список при отсутствии книг")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllExpectedNullWhenNoRecords() {
        List<Book> bookList = bookDao.getAll();
        for (Book book :
                bookList) {
            bookDao.deleteById(book.getId());
        }
        assertEquals(new ArrayList<>(), bookDao.getAll());
    }

    @DisplayName("deleteById удалил запись")
    @Test
    void deleteById() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, DUMMY_BOOK));
        bookDao.deleteById(bookId);
        assertNull(bookDao.getById(bookId));
    }

    @DisplayName("setBookAuthor не нашел книгу")
    @Test
    void setBookAuthorNoBookFound() {
        assertThrows(NoBookFoundException.class, () -> bookDao.setBookAuthor(DUMMY_ID, TEST_AUTHOR_ID_1));
    }

    @DisplayName("setBookAuthor не нашел автора")
    @Test
    void setBookAuthorNoAuthorFound() {
        assertThrows(NoAuthorFoundException.class, () -> bookDao.setBookAuthor(TEST_ID_1, DUMMY_ID));
    }

    @DisplayName("setBookAuthor добавил автора")
    @Test
    void setBookAuthorAddNewAuthor() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long authorId = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        int res = bookDao.setBookAuthor(bookId, authorId);
        List<Author> authorList = bookDao.getBookAuthor(bookId);
        assertTrue(1 == res && authorList.contains(new Author(authorId, CURRENT_AUTHOR)));
    }

    @DisplayName("setBookAuthor проверил, что автор уже добавлен и не выдал ошибку")
    @Test
    void setBookAuthorAuthorExists() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long authorId = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        bookDao.setBookAuthor(bookId, authorId);
        bookDao.setBookAuthor(bookId, authorId);
        assertEquals(1, bookDao.getBookAuthor(bookId).size());
    }

    @DisplayName("setBookAuthor добавил второго автора")
    @Test
    void setBookAuthorAddSecondAuthor() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long authorId = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        bookDao.setBookAuthor(bookId, authorId);
        authorId = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        bookDao.setBookAuthor(bookId, authorId);
        assertEquals(2, bookDao.getBookAuthor(bookId).size());
    }

    @DisplayName("getBookAuthor вернул автора")
    @Test
    void getBookAuthor() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long authorId = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        bookDao.setBookAuthor(bookId, authorId);
        List<Author> authorList = bookDao.getBookAuthor(bookId);
        assertTrue(authorList.size() == 1 && authorList.contains(new Author(authorId, CURRENT_AUTHOR)));
    }

    @DisplayName("getBookAuthor вернул двух авторов")
    @Test
    void getBookAuthors() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long authorId = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR));
        long authorId2 = authorDao.insert(new Author(DUMMY_ID, CURRENT_AUTHOR_2));
        bookDao.setBookAuthor(bookId, authorId);
        bookDao.setBookAuthor(bookId, authorId2);
        List<Author> authorList = bookDao.getBookAuthor(bookId);
        assertTrue(authorList.size() == 2 && authorList.contains(new Author(authorId, CURRENT_AUTHOR)) && authorList.contains(new Author(authorId2, CURRENT_AUTHOR_2)));
    }

    @DisplayName("getBookAuthor вернул пустой список при отсутствии авторов")
    @Test
    void getBookAuthorNoAuthorFound() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        assertEquals(new ArrayList<>(), bookDao.getBookAuthor(bookId));
    }

    @DisplayName("setBookCategory не нашел книгу")
    @Test
    void setBookCategoryNoBookFound() {
        assertThrows(NoBookFoundException.class, () -> bookDao.setBookCategory(DUMMY_ID, TEST_CATEGORY_ID_1));
    }

    @DisplayName("setBookCategory не нашел категорию")
    @Test
    void setBookCategoryNoCategoryFound() {
        assertThrows(NoCategoryFoundException.class, () -> bookDao.setBookCategory(TEST_ID_1, DUMMY_ID));
    }

    @DisplayName("setBookCategory добавил категорию")
    @Test
    void setBookCategoryAddNewCategory() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        int res = bookDao.setBookCategory(bookId, categoryId);
        List<Category> categoryList = bookDao.getBookCategory(bookId);
        assertTrue(1 == res && categoryList.contains(new Category(categoryId, CURRENT_CATEGORY)));
    }

    @DisplayName("setBookCategory проверил, что категория уже добавлена и не выдал ошибку")
    @Test
    void setBookCategoryCategoryExists() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        bookDao.setBookCategory(bookId, categoryId);
        bookDao.setBookCategory(bookId, categoryId);
        assertEquals(1, bookDao.getBookCategory(bookId).size());
    }

    @DisplayName("setBookCategory добавил вторую категорию")
    @Test
    void setBookCategoryAddSecondCategory() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        bookDao.setBookCategory(bookId, categoryId);
        categoryId = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        bookDao.setBookCategory(bookId, categoryId);
        assertEquals(2, bookDao.getBookCategory(bookId).size());
    }

    @DisplayName("getBookCategory вернул категорию")
    @Test
    void getBookCategory() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        bookDao.setBookCategory(bookId, categoryId);
        List<Category> categoryList = bookDao.getBookCategory(bookId);
        assertTrue(categoryList.size() == 1 && categoryList.contains(new Category(categoryId, CURRENT_CATEGORY)));
    }

    @DisplayName("getBookCategory вернул две категории")
    @Test
    void getBookCategorys() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        long categoryId = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY));
        long categoryId2 = categoryDao.insert(new Category(DUMMY_ID, CURRENT_CATEGORY_2));
        bookDao.setBookCategory(bookId, categoryId);
        bookDao.setBookCategory(bookId, categoryId2);
        List<Category> categoryList = bookDao.getBookCategory(bookId);
        assertTrue(categoryList.size() == 2 && categoryList.contains(new Category(categoryId, CURRENT_CATEGORY)) && categoryList.contains(new Category(categoryId2, CURRENT_CATEGORY_2)));
    }

    @DisplayName("getBookCategory вернул пустой список при отсутствии категорий")
    @Test
    void getBookCategoryNoCategoryFound() {
        long bookId = bookDao.insert(new Book(DUMMY_ID, CURRENT_BOOK));
        assertEquals(new ArrayList<>(), bookDao.getBookCategory(bookId));
    }

}