package ru.otus.homework08.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName(" должен корректно сохранять автора с отсутствующей в БД книгой")
    @Test
    void shouldCorrectSaveAuthorWithNewBook() {
        Author author = new Author("Автор 101");
        author.getBooks().add(new Book("Книга 101"));
        val actual = authorRepository.save(author);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(author.getName());
    }

    @DisplayName("при удалении Книги должен удалить ее из автора")
    @Test
    void shouldRemoveBookFromAuthorBooksWhenRemovingBook() {
        val authors = authorRepository.findAll();
        val author = authors.get(0);
        val books = author.getBooks();
        val firstBook = books.get(0);

        bookRepository.deleteById(firstBook.getId());

        val expectedBooksArrayLength = books.size() - 1;
        val actualBookOptional = authorRepository.findById(author.getId());
        assertThat(actualBookOptional)
                .isNotEmpty().get()
                .matches(s -> s.getBooks() != null && s.getBooks().size() == expectedBooksArrayLength);

        val actualBooksArrayLength = authorRepository.getBooksArrayLengthByAuthorId(author.getId());
        assertThat(actualBooksArrayLength).isEqualTo(expectedBooksArrayLength);
    }
}