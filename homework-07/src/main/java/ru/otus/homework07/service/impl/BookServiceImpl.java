package ru.otus.homework07.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework07.exception.NoAuthorFoundException;
import ru.otus.homework07.exception.NoBookFoundException;
import ru.otus.homework07.exception.NoCategoryFoundException;
import ru.otus.homework07.model.Author;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Category;
import ru.otus.homework07.model.Comment;
import ru.otus.homework07.repository.AuthorRepositoryJpa;
import ru.otus.homework07.repository.BookRepositoryJpa;
import ru.otus.homework07.repository.CategoryRepositoryJpa;
import ru.otus.homework07.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepositoryJpa repositoryJpa;
    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final CategoryRepositoryJpa categoryRepositoryJpa;

    public BookServiceImpl(BookRepositoryJpa repositoryJpa, AuthorRepositoryJpa authorRepositoryJpa, CategoryRepositoryJpa categoryRepositoryJpa) {
        this.repositoryJpa = repositoryJpa;
        this.authorRepositoryJpa = authorRepositoryJpa;
        this.categoryRepositoryJpa = categoryRepositoryJpa;
    }

    @Transactional(readOnly = true)
    public String getBookByIdToString(long bookId) {
        Book book = repositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        return getBookToString(book);
    }

    @Transactional(readOnly = true)
    public String getAllBooksToString() {
        String res = new String();
        for (Book book :
                repositoryJpa.findAll()) {
            res = res + String.format("%s \r\n", getBookToString(book));
        }
        return res;
    }

    @Override
    @Transactional
    public void setBookAuthor(long bookId, long authorId) {
        Book book = repositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Author author = authorRepositoryJpa.findById(authorId).orElseThrow(() -> new NoAuthorFoundException(new Throwable()));
        List<Author> authors = book.getAuthors();
        if (!authors.contains(author)) {
            authors.add(author);
            book.setAuthors(authors);
            repositoryJpa.save(book);
        }
    }

    @Override
    @Transactional
    public Book setBookCategory(long bookId, long categoryId) {
        Book book = repositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Category category = categoryRepositoryJpa.findById(categoryId).orElseThrow(() -> new NoCategoryFoundException(new Throwable()));
        List<Category> categories = book.getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            book.setCategories(categories);
            repositoryJpa.save(book);
        }
        return book;
    }

    public String getBookToString(Book book) {
        if (book == null) {
            return "Book is null";
        } else {
            return String.format("%d | %s | %s | %s", book.getId(), book.getName(), getBookAutorToString(book), getBookCategoryToString(book));
        }
    }

    public String getBookCommentToString(Book book, List<Comment> commentList) {
        String res = new String();
        if (book == null) {
            return "Book is null";
        } else {
            res = String.format("Комментарии для книги \"%s\"\r\n", book.getName());
            for (Comment comment :
                    commentList) {
                res = res + String.format("%s | %s \r\n", comment.getId(), comment.getText());
            }
        }
        return res;
    }

    private String getBookAutorToString(Book book) {
        String authors = new String();
        List<Author> authorList = book.getAuthors();
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
        List<Category> categoryList = book.getCategories();
        if (categoryList != null) {
            for (Category category :
                    categoryList) {
                categories = categories + category.getName() + "; ";
            }
        }
        return categories;
    }
}
