package ru.otus.homework08.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Category;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository должен ")
class BookRepositoryTest extends AbstractRepositoryTest {
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;
    private static final int EXPECTED_NUMBER_OF_CATEGORIES = 2;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @DisplayName(" возвращать корректный список авторов книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCorrectAuthorsList() {
        val books = bookRepository.findAll();
        val book = books.get(0);
        val authors = book.getAuthors();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS);

        val actualAuthors = bookRepository.getBookAuthorsByBookId(book.getId());
        assertThat(actualAuthors).containsExactlyInAnyOrder(authors.toArray(new Author[0]));
    }

    @DisplayName(" возвращать корректный список категорий книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCorrectCategoriesList() {
        val books = bookRepository.findAll();
        val book = books.get(0);
        val categories = book.getCategories();
        assertThat(categories).isNotNull().hasSize(EXPECTED_NUMBER_OF_CATEGORIES);

        val actualCategories = bookRepository.getBookCategoriesByBookId(book.getId());
        assertThat(actualCategories).containsExactlyInAnyOrder(categories.toArray(new Category[0]));
    }

    @DisplayName(" должен корректно сохранять книгу с отсутствующим в БД автором")
    @Test
    void shouldCorrectSaveBooktWithNewAuthor() {
        Book book = new Book("Книга 100");
        book.getAuthors().add(new Author("Автор 100"));
        val actual = bookRepository.save(book);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(book.getName());
    }

    @DisplayName(" должен корректно сохранять книгу с отсутствующей в БД категорией")
    @Test
    void shouldCorrectSaveBooktWithNewCategory() {
        Book book = new Book("Книга 101");
        book.getCategories().add(new Category("Категория 101"));
        val actual = bookRepository.save(book);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(book.getName());
    }

    @DisplayName("при удалении Автора должен удалить его из книги")
    @Test
    void shouldRemoveAuthorFromBookAuthorsWhenRemovingAuthor() {
        val books = bookRepository.findAll();
        val book = books.get(1);
        val authors = book.getAuthors();
        val firstAuthor = authors.get(0);

        authorRepository.deleteById(firstAuthor.getId());

        val expectedAuthorsArrayLength = authors.size() - 1;
        val actualBookOptional = bookRepository.findById(book.getId());
        assertThat(actualBookOptional)
                .isNotEmpty().get()
                .matches(s -> s.getAuthors() != null && s.getAuthors().size() == expectedAuthorsArrayLength);

        val actualAuthorsArrayLength = bookRepository.getAuthorsArrayLengthByBookId(book.getId());
        assertThat(actualAuthorsArrayLength).isEqualTo(expectedAuthorsArrayLength);
    }

    @DisplayName("при удалении Категории должен удалить его из книги")
    @Test
    void shouldRemoveCategoryFromBookCategoriesWhenRemovingCategory() {
        val books = bookRepository.findAll();
        val book = books.get(1);
        val categories = book.getCategories();
        val firstCategory = categories.get(0);

        categoryRepository.delete(firstCategory);

        val expectedCategoriesArrayLength = categories.size() - 1;
        val actualBookOptional = bookRepository.findById(book.getId());
        assertThat(actualBookOptional)
                .isNotEmpty().get()
                .matches(s -> s.getCategories() != null && s.getCategories().size() == expectedCategoriesArrayLength);

        val actualCategoriesArrayLength = bookRepository.getCategoriesArrayLengthByBookId(book.getId());
        assertThat(actualCategoriesArrayLength).isEqualTo(expectedCategoriesArrayLength);
    }
}