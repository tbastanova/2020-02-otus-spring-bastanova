package ru.otus.homework08.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework08.exception.NoBookFoundException;
import ru.otus.homework08.exception.NoCommentFoundException;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.service.BookService;
import ru.otus.homework08.service.CommentService;

@ShellComponent
public class CommentShellCommands {
    private final CommentService commentService;
    private final BookService bookService;

    private static final String DUMMY_ID = "0";
    private static final String NO_COMMENT_FOUND_EXCEPTION_TEXT = "Комментарий с id = %s не найден";
    private static final String NO_BOOK_FOUND_EXCEPTION_TEXT = "Книга с id = %s не найдена";

    public CommentShellCommands(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @ShellMethod(value = "Insert comment", key = {"insertcomment", "ik"})
    public String insertAuthor(@ShellOption(defaultValue = "New Comment") String commentText) {
        Comment comment = commentService.save(new Comment(commentText));
        return String.format("Создан комментарий id = %s", comment.getId());
    }

    @ShellMethod(value = "Get comment by id", key = {"getcomment", "gk"})
    public String getCommentById(@ShellOption String commentId) {
        try {
            Comment comment = commentService.findById(commentId);
            return String.format("Комментарий: id = %s, name = %s", comment.getId(), comment.getText());
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        }
    }

    @ShellMethod(value = "Update comment", key = {"updatecomment", "uk"})
    public String updateComment(@ShellOption String commentId, String commentText) {
        commentService.save(new Comment(commentId, commentText));
        try {
            return String.format("Комментарий: id = %s, name = %s", commentId, commentText);
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        }
    }

    @ShellMethod(value = "Delete comment", key = {"deletecomment", "dk"})
    public String deleteCommentById(@ShellOption String commentId) {
        try {
            commentService.deleteById(commentId);
            return String.format("Удален комментарий id = %s", commentId);
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        }
    }

    @ShellMethod(value = "Get all comments", key = {"getallcomment", "gak"})
    public void getAllComment() {
        for (Comment comment :
                commentService.findAll()) {
            System.out.println(comment.getId() + " | " + comment.getText());
        }
    }

    @ShellMethod(value = "Get comment book", key = {"getcommentbook", "gkb"})
    public void getCommentBook(@ShellOption String commentId) {
        try {
            Comment comment = commentService.findById(commentId);
            Book book = comment.getBook();
            System.out.println("Комментарий относится к книге");
            System.out.printf("%s | %s", book.getId(), book.getName());
            System.out.println();
        } catch (NoCommentFoundException e) {
            System.out.println(String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId));
        }
    }

    @ShellMethod(value = "Set comment book", key = {"setcommentbook", "skb"})
    public String setBookToComment(@ShellOption String commentId, String bookId) {
        try {
            commentService.updateBookId(commentId, bookService.findById(bookId));
            return String.format("Комментарий привязан к книге");
        } catch (NoCommentFoundException e) {
            return String.format(NO_COMMENT_FOUND_EXCEPTION_TEXT, commentId);
        } catch (NoBookFoundException e) {
            return String.format(NO_BOOK_FOUND_EXCEPTION_TEXT, bookId);
        }
    }
}
