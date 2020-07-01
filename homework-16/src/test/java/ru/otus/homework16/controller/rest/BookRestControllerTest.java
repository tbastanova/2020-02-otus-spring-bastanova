package ru.otus.homework16.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.homework16.exception.NoBookFoundException;
import ru.otus.homework16.model.Book;
import ru.otus.homework16.repository.BookRepositoryJpa;
import ru.otus.homework16.service.BookService;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RestController для книг")
@EnableConfigurationProperties
@SpringBootTest
@AutoConfigureMockMvc
@Import({BookRestController.class})
class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    private static final long FIRST_BOOK_ID = 1L;
    private static final String NEW_BOOK_NAME = "New Book";

    @Test
    @DisplayName(" успешно создает книгу по api POST")
    void insertBook() {
        try {
            long cnt = bookRepositoryJpa.count();
            mockMvc.perform(MockMvcRequestBuilders.post("/book")
                    .param("id", "0")
                    .param("name", NEW_BOOK_NAME))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
            assertEquals(cnt + 1, bookRepositoryJpa.count());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(" удаляет книгу по api DELETE")
    void deleteBook() {
        Book book = bookService.findById(FIRST_BOOK_ID);
        assertNotNull(book);
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/book/" + FIRST_BOOK_ID));
            assertThrows(NoBookFoundException.class, () -> bookService.findById(FIRST_BOOK_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName(" возвращает книгу по api GET")
    void findBookById() {
        try {
            Book book = bookService.findById(FIRST_BOOK_ID);

            mockMvc.perform(MockMvcRequestBuilders.get("/book/" + FIRST_BOOK_ID))
                    .andExpect(MockMvcResultMatchers.content().string(containsString(book.getName())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName(" обновляет книгу по api PATCH")
    void patchBook() {
        try {
            String oldBookName = bookService.findById(FIRST_BOOK_ID).getName();
            String newBookName = oldBookName + "TestPatch";

            mockMvc.perform(MockMvcRequestBuilders.patch("/book/" + FIRST_BOOK_ID)
                    .param("id", String.valueOf(FIRST_BOOK_ID))
                    .param("name", newBookName));

            Book changedBook = bookService.findById(FIRST_BOOK_ID);

            assertNotEquals(oldBookName, changedBook.getName());
            assertEquals(newBookName, changedBook.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}