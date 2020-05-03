package ru.otus.homework07.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework07.exception.NoBookFoundException;
import ru.otus.homework07.exception.NoCommentFoundException;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Comment;
import ru.otus.homework07.repository.CommentRepositoryJpa;
import ru.otus.homework07.service.CommentService;

@ShellComponent
public class CommentShellCommands {
    private final CommentRepositoryJpa repositoryJpa;
    private final CommentService commentService;

    private static final long DUMMY_ID = -1;
    private static final String NO_COMMENT_FOUND_EXCEPTION_TEXT = "Комментарий с id = %d не найден";
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %d не найдена";

    public CommentShellCommands(CommentRepositoryJpa repositoryJpa, CommentService commentService) {
        this.repositoryJpa = repositoryJpa;
        this.commentService = commentService;
    }

    @ShellMethod(value = "Insert comment", key = {"insertcomment", "ik"})
    public String insertAuthor(@ShellOption(defaultValue = "New Comment") String commentText) {
        Comment comment = repositoryJpa.save(new Comment(DUMMY_ID, commentText));
        return String.format("Создан комментарий id = %d", comment.getId());
    }

    @ShellMethod(value = "Get comment by id", key = {"getcomment", "gk"})
    public String getCommentById(@ShellOption long commentId) {
        try {
            Comment comment = repositoryJpa.findById(commentId).orElseThrow(() -> new NoCommentFoundException(new Throwable()));
            return String.format("Комментарий: id = %d, name = %s", comment.getId(), comment.getText());
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        }
    }

    @ShellMethod(value = "Update comment", key = {"updatecomment", "uk"})
    public String updateComment(@ShellOption long commentId, String commentText) {
        repositoryJpa.save(new Comment(commentId, commentText));
        try {
            return String.format("Комментарий: id = %d, name = %s", commentId, commentText);
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        }
    }

    @ShellMethod(value = "Delete comment", key = {"deletecomment", "dk"})
    public String deleteCommentById(@ShellOption long commentId) {
        try {
            Comment comment = repositoryJpa.findById(commentId).orElseThrow(() -> new NoCommentFoundException(new Throwable()));
            repositoryJpa.delete(comment);
            return String.format("Удален комментарий id = %d", commentId);
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        }
    }

    @ShellMethod(value = "Get all comments", key = {"getallcomment", "gak"})
    public void getAllComment() {
        for (Comment comment :
                repositoryJpa.findAll()) {
            System.out.println(comment.getId() + " | " + comment.getText());
        }
    }

    @ShellMethod(value = "Get comment book", key = {"getcommentbook", "gkb"})
    public void getCommentBook(@ShellOption long commentId) {
        try {
            Comment comment = repositoryJpa.findById(commentId).orElseThrow(() -> new NoCommentFoundException(new Throwable()));
            Book book = comment.getBook();
            System.out.println("Комментарий относится к книге");
            System.out.printf("%s | %s", book.getId(), book.getName());
            System.out.println();
        } catch (NoCommentFoundException e) {
            System.out.println(String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId));
        }
    }

    @ShellMethod(value = "Set comment book", key = {"setcommentbook", "skb"})
    public String setBookToComment(@ShellOption long commentId, long bookId) {
        try {
            commentService.updateBookId(commentId, bookId);
            return String.format("Комментарий привязан к книге");
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        } catch (NoBookFoundException e) {
            return String.format(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
        }
    }
}
