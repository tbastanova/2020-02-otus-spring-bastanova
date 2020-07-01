package ru.otus.homework10.controller.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.homework10.service.AuthorService;
import ru.otus.homework10.service.BookService;
import ru.otus.homework10.service.CategoryService;
import ru.otus.homework10.service.CommentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DisplayName("PageController для книг")
@EnableConfigurationProperties
@WebMvcTest
@AutoConfigureMockMvc
@Import({BookPageController.class})
class BookPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName(" возвращает view listBook при запросе /")
    public void home() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(MockMvcResultMatchers.view().name("listBook"));
    }

    @Test
    @DisplayName(" возвращает view editBook при запросе /editBook/1")
    public void editBook() throws Exception {
        this.mockMvc.perform(get("/editBook/1")).andExpect(MockMvcResultMatchers.view().name("editBook"));
    }
}