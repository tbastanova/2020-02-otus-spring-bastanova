package ru.otus.homework13.service.impl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.repository.BookRepositoryJpa;
import ru.otus.homework13.service.BookService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Security для BookService")
@EnableConfigurationProperties
@SpringBootTest
@AutoConfigureMockMvc
@Import(UserDetailsServiceImpl.class)
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private MutableAclService mutableAclService;

    private static final long FIRST_BOOK_ID = 1L;
    private static final long SECOND_BOOK_ID = 2L;
    private static final long THIRD_BOOK_ID = 3L;
    private static final String NEW_BOOK_NAME = "New Book";

    @Test
    @DisplayName(" findAll возвращает все что должен для роли ADMIN")
    @WithMockUser(roles = "ADMIN")
    public void
    findAllForAdmin() {
        List<Book> books = bookService.findAll();

        assertNotNull(books);
        assertEquals(3, books.size());
        assertEquals(FIRST_BOOK_ID, books.get(0).getId());
        assertEquals(SECOND_BOOK_ID, books.get(1).getId());
        assertEquals(THIRD_BOOK_ID, books.get(2).getId());
    }

    @Test
    @DisplayName(" findAll возвращает все что должен для роли EDITOR")
    @WithMockUser(roles = {"EDITOR"})
    public void
    findAllForEditor() {
        List<Book> books = bookService.findAll();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals(FIRST_BOOK_ID, books.get(0).getId());
        assertEquals(THIRD_BOOK_ID, books.get(1).getId());
    }

    @Test
    @DisplayName(" findAll возвращает все что должен для роли USER")
    @WithMockUser(roles = {"USER"})
    public void
    findAllForUser() {
        List<Book> books = bookService.findAll();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(THIRD_BOOK_ID, books.get(0).getId());
    }

    @Test
    @DisplayName(" findById возвращает книгу которую может для роли USER")
    @WithMockUser(roles = {"USER"})
    public void
    findByIdForUserFoundBook() {
        Book book = bookService.findById(THIRD_BOOK_ID);

        assertNotNull(book);
        assertEquals(THIRD_BOOK_ID, book.getId());
    }

    @Test
    @DisplayName(" findById не возвращает книгу которую не может для роли USER")
    @WithMockUser(roles = {"USER"})
    public void
    findByIdForUserCanNotFoundBook() {
        assertThrows(AccessDeniedException.class, () -> bookService.findById(FIRST_BOOK_ID));
    }

    @Test
    @DisplayName(" save изменяет запись доступную для редактирования роли EDITOR")
    @WithMockUser(roles = {"EDITOR"})
    public void editForEditorIsOk() {
        Book book = bookService.findById(FIRST_BOOK_ID);

        assertNotNull(book);

        String origBookName = book.getName();
        String newBookName = origBookName + "Test";
        book.setName(newBookName);
        bookService.save(book);

        Book changedBook = bookService.findById(FIRST_BOOK_ID);
        assertNotEquals(origBookName, changedBook.getName());
        assertEquals(newBookName, changedBook.getName());
    }

    @Test
    @DisplayName(" save не дает редактировать запись недоступную роли EDITOR")
    @WithMockUser(roles = {"EDITOR"})
    public void editForEditorIsNotOk() {
        Book book = bookService.findById(THIRD_BOOK_ID);

        assertNotNull(book);

        String origBookName = book.getName();
        String newBookName = origBookName + "Test";
        book.setName(newBookName);
        assertThrows(AccessDeniedException.class, () -> bookService.save(book));
    }

    @Test
    @DisplayName(" роль EDITOR не может создавать новую книгу")
    @WithMockUser(roles = {"EDITOR"})
    public void createForEditorIsNotOk() {
        assertThrows(AccessDeniedException.class, () -> bookService.save(new Book(0, NEW_BOOK_NAME)));
    }

    @Test
    @DisplayName(" роль ADMIN может создавать новую книгу")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createForAdminIsOk() {

        Book newBook = bookService.save(new Book(0, NEW_BOOK_NAME));
        Book book = bookRepositoryJpa.findById(newBook.getId()).get();

        assertNotNull(book);
        assertEquals(NEW_BOOK_NAME, book.getName());
    }

    @Test
    @DisplayName(" роль EDITOR не может удалять книгу")
    @WithMockUser(roles = {"EDITOR"})
    public void deleteForEditorIsNotOk() {
        assertThrows(AccessDeniedException.class, () -> bookService.deleteById(FIRST_BOOK_ID));
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(" роль ADMIN может удалять книгу")
    @WithMockUser(roles = {"ADMIN"})
    public void deleteByIdForAdminIsOk() {
        Book book = bookService.findById(FIRST_BOOK_ID);

        assertNotNull(book);
        bookService.deleteById(FIRST_BOOK_ID);

        assertTrue(bookRepositoryJpa.findById(FIRST_BOOK_ID).isEmpty());
    }
}