package ru.otus.homework12.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.otus.homework12.exception.NoBookFoundException;
import ru.otus.homework12.model.Author;
import ru.otus.homework12.model.Book;
import ru.otus.homework12.model.Category;
import ru.otus.homework12.repository.BookRepositoryJpa;
import ru.otus.homework12.service.AuthorService;
import ru.otus.homework12.service.BookService;
import ru.otus.homework12.service.CategoryService;
import ru.otus.homework12.service.CommentService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepositoryJpa bookRepositoryJpa;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa, AuthorService authorService, CategoryService categoryService, CommentService commentService) {
        this.bookRepositoryJpa = bookRepositoryJpa;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

    @Override
    public List<Book> findAll() {
        return bookRepositoryJpa.findAll();
    }

    @Override
    public Book findById(long id) {
        return bookRepositoryJpa.findById(id).orElseThrow(() -> new NoBookFoundException(new Throwable()));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != 0) {
            Book repBook = bookRepositoryJpa.findById(book.getId()).orElseThrow(() -> new NoBookFoundException(new Throwable()));
            if (book.getAuthors() == null) {
                book.setAuthors(repBook.getAuthors());
            }
            if (book.getCategories() == null) {
                book.setCategories(repBook.getCategories());
            }
        }
        return bookRepositoryJpa.save(book);
    }

    @Transactional
    public void deleteById(long bookId) {
        bookRepositoryJpa.deleteById(bookId);
    }

    @Override
    @Transactional
    public Book setBookAuthor(long bookId, long authorId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Author author = authorService.findById(authorId);
        List<Author> authors = book.getAuthors();
        if (!authors.contains(author)) {
            authors.add(author);
            book.setAuthors(authors);
            bookRepositoryJpa.save(book);
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookAuthor(long bookId, long authorId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Author author = authorService.findById(authorId);
        book.getAuthors().remove(author);
        bookRepositoryJpa.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book setBookCategory(long bookId, long categoryId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Category category = categoryService.findById(categoryId);
        List<Category> categories = book.getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            book.setCategories(categories);
            bookRepositoryJpa.save(book);
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookCategory(long bookId, long categoryId) {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Category category = categoryService.findById(categoryId);
        book.getCategories().remove(category);
        bookRepositoryJpa.save(book);
        return book;
    }

    @Override
    public Model getEditBookModel(long bookId, Model model) {
        model.addAttribute("book", findById(bookId));
        model.addAttribute("comments", commentService.findByBook_Id(bookId));
        return model;
    }

}
