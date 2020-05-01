package ru.otus.homework06.repository.impl;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.exception.NoCommentFoundException;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;
import ru.otus.homework06.service.impl.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({CommentRepositoryJpaImpl.class, BookRepositoryJpaImpl.class, CommentServiceImpl.class})
class CommentRepositoryJpaImplTest {

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 12;
    private static final long FIRST_COMMENT_ID = 1L;
    private static final String CURRENT_COMMENT = "Current Comment";
    private static final long DUMMY_ID = 0;
    private static final String DUMMY_COMMENT = "Dummy Comment";
    private static final String CHANGED_COMMENT = "Changed Comment";
    private static final String CURRENT_BOOK = "Current Book";

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private CommentRepositoryJpaImpl repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepositoryJpaImpl bookRepositoryJpa;

    @Autowired
    private CommentServiceImpl commentService;

    @DisplayName(" должен загружать информацию о нужном комментарии по его id")
    @Test
    void findById() {
        val optionalActualComment = repositoryJpa.findById(FIRST_COMMENT_ID);
        val expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("должен загружать список все комментариев")
    @Test
    void findAll() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val comments = repositoryJpa.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getText().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("insert отработал корректно")
    @Test
    void insert() {
        long commentId = repositoryJpa.insert(new Comment(DUMMY_ID, CURRENT_COMMENT));
        val optionalActualComment = repositoryJpa.findById(commentId);
        val expectedComment = em.find(Comment.class, commentId);
        assertThat(optionalActualComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("update отработал корректно")
    @Test
    void update() {
        val firstComment = em.find(Comment.class, FIRST_COMMENT_ID);
        String oldName = firstComment.getText();
        em.detach(firstComment);

        repositoryJpa.update(new Comment(FIRST_COMMENT_ID, CHANGED_COMMENT));
        val updatedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(updatedComment.getText()).isNotEqualTo(oldName).isEqualTo(CHANGED_COMMENT);
    }

    @DisplayName("update не нашел комментарий и вернул ошибку")
    @Test
    void updateNoRecordFound() {
        assertThrows(NoCommentFoundException.class, () -> repositoryJpa.update(new Comment(DUMMY_ID, DUMMY_COMMENT)));
    }

    @DisplayName("getById не нашел несуществующий комментарий")
    @Test
    void getByIdNoFound() {
        assertEquals(Optional.empty(), repositoryJpa.findById(DUMMY_ID));
    }

    @DisplayName("getAll вернул пустой список при отсутствии комментариев")
    @Test
    void getAllExpectedNullWhenNoRecords() {
        List<Comment> commentList = repositoryJpa.findAll();
        for (Comment comment :
                commentList) {
            repositoryJpa.deleteById(comment.getId());
        }

        assertEquals(new ArrayList<>(), repositoryJpa.findAll());
    }

    @DisplayName("deleteById удалил запись")
    @Test
    void deleteById() {
        val firstComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(firstComment).isNotNull();
        em.detach(firstComment);

        repositoryJpa.deleteById(FIRST_COMMENT_ID);
        val deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(deletedComment).isNull();
    }

    @DisplayName("checkExists проверил, что запись существует и вернул true")
    @Test
    void checkExistsReturnTrue() {
        long commentId = repositoryJpa.insert(new Comment(DUMMY_ID, CURRENT_COMMENT));
        assertTrue(repositoryJpa.checkExists(commentId) && !repositoryJpa.findById(commentId).isEmpty());
    }

    @DisplayName("checkExists проверил, что запись не существует и вернул false")
    @Test
    void checkExistsReturnFalse() {
        assertTrue(!repositoryJpa.checkExists(DUMMY_ID) && repositoryJpa.findById(DUMMY_ID).isEmpty());
    }

    @DisplayName("findByBookId вернул список комментариев")
    @Test
    void findByBookId() {
        Book book = new Book(DUMMY_ID, CURRENT_BOOK);
        long bookId = bookRepositoryJpa.insert(book);
        long firstCommentId = commentService.addBookComment(bookId, CURRENT_COMMENT);
        long secondCommentId = commentService.addBookComment(bookId, CHANGED_COMMENT);

        List<Comment> commentList = new ArrayList<>();
        Comment firstComment = new Comment(firstCommentId, CURRENT_COMMENT);
        firstComment.setBook(book);
        commentList.add(firstComment);
        Comment secondComment = new Comment(secondCommentId, CHANGED_COMMENT);
        secondComment.setBook(book);
        commentList.add(secondComment);

        List<Comment> foundComments = repositoryJpa.findByBookId(bookId);
        assertTrue(foundComments != null && foundComments.equals(commentList));
    }

    @DisplayName("findByBookId не нашел комментариев и вернул пустой список")
    @Test
    void findNoOneByBookId() {
        Book book = new Book(DUMMY_ID, CURRENT_BOOK);
        long bookId = bookRepositoryJpa.insert(book);

        List<Comment> foundComments = repositoryJpa.findByBookId(bookId);
        assertTrue(foundComments.equals(new ArrayList<>()));
    }

    @DisplayName("findByBookId не нашел книгу и вернул пустой список")
    @Test
    void findByBookIdNoBookException() {
        assertTrue(repositoryJpa.findByBookId(DUMMY_ID).equals(new ArrayList<>()));
    }

    @DisplayName("updateBookId привязал комментарий к книге")
    @Test
    void updateBookId() {
        Comment comment = new Comment(DUMMY_ID, CURRENT_COMMENT);
        long commentId = repositoryJpa.insert(comment);
        Book book = new Book(DUMMY_ID, CURRENT_BOOK);
        long bookId = bookRepositoryJpa.insert(book);
        repositoryJpa.updateBookId(comment, book);
        List<Comment> foundComments = repositoryJpa.findByBookId(bookId);
        assertTrue(foundComments.contains(comment));
    }
}