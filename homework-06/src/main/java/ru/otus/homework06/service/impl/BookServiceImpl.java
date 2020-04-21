package ru.otus.homework06.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Category;
import ru.otus.homework06.model.Comment;
import ru.otus.homework06.repository.impl.BookRepositoryJpaImpl;
import ru.otus.homework06.repository.impl.CommentRepositoryJpaImpl;
import ru.otus.homework06.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepositoryJpaImpl bookRepositoryJpa;

    @Autowired
    private CommentRepositoryJpaImpl commentRepositoryJpa;

    @Transactional(readOnly = true)
    public String getBookToString(Book book) {
        if (book == null) {
            return "Book is null";
        } else {
            return String.format("%d | %s | %s | %s", book.getId(), book.getName(), getBookAutorToString(book), getBookCategoryToString(book));
        }
    }

    @Transactional(readOnly = true)
    public String getBookCommentToString(long bookId) {
        Optional<Book> book = bookRepositoryJpa.findById(bookId);
        String res = new String();
        if (book == null) {
            return "Book is null";
        } else {
            res = String.format("Комментарии для книги \"%s\"\r\n", book.get().getName());
            List<Comment> commentList = commentRepositoryJpa.findByBookId(bookId);
            for (Comment comment :
                    commentList) {
                res = res + String.format("%s | %s \r\n", comment.getId(), comment.getText());
            }
        }
        return res;
    }

    private String getBookAutorToString(Book book) {
        String authors = new String();
        List<Author> authorList = bookRepositoryJpa.getBookAuthor(book.getId());
        if (authorList != null) {
            for (Author author :
                    authorList) {
                authors = authors + author.getName() + "; ";
            }
        }
        return authors;
    }

    private String getBookCategoryToString(Book book) {
        String categories = new String();
        List<Category> categoryList = bookRepositoryJpa.getBookCategory(book.getId());
        if (categoryList != null) {
            for (Category category :
                    categoryList) {
                categories = categories + category.getName() + "; ";
            }
        }
        return categories;
    }
}
