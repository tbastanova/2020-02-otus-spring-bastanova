package ru.otus.homework08.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Category;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName(" должен корректно сохранять категорию с отсутствующей в БД книгой")
    @Test
    void shouldCorrectSaveCategoryWithNewBook() {
        Category category = new Category("Категория 102");
        Book b = new Book("Книга 102");
        category.getBooks().add(b);
        val actual = categoryRepository.save(category);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(category.getName());
    }

    @DisplayName("при удалении Книги должен удалить ее из категории")
    @Test
    void shouldRemoveBookFromCategoryBooksWhenRemovingBook() {
        val categories = categoryRepository.findAll();
        val category = categories.get(0);
        val books = category.getBooks();
        val firstBook = books.get(0);

        bookRepository.deleteById(firstBook.getId());

        val expectedBooksArrayLength = books.size() - 1;
        val actualBookOptional = categoryRepository.findById(category.getId());
        assertThat(actualBookOptional)
                .isNotEmpty().get()
                .matches(s -> s.getBooks() != null && s.getBooks().size() == expectedBooksArrayLength);

        val actualBooksArrayLength = categoryRepository.getBooksArrayLengthByCategoryId(category.getId());
        assertThat(actualBooksArrayLength).isEqualTo(expectedBooksArrayLength);
    }
}