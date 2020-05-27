package ru.otus.homework08.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.exception.NoBookFoundException;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Category;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.service.AuthorService;
import ru.otus.homework08.service.BookService;
import ru.otus.homework08.service.CategoryService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoBookFoundException(new Throwable()));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null) {
            Book repBook = bookRepository.findById(book.getId()).orElseThrow(() -> new NoBookFoundException(new Throwable()));
            if (book.getAuthors() == null || book.getAuthors().size() == 0) {
                book.setAuthors(repBook.getAuthors());
            }
            if (book.getCategories() == null || book.getCategories().size() == 0) {
                book.setCategories(repBook.getCategories());
            }
        }
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteById(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public boolean existsById(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    @Transactional
    public Book setBookAuthor(String bookId, String authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Author author = authorService.findById(authorId);
        List<Author> authors = book.getAuthors();
        if (!authors.contains(author)) {
            authors.add(author);
            book.setAuthors(authors);
            bookRepository.save(book);
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookAuthor(String bookId, String authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Author author = authorService.findById(authorId);
        book.getAuthors().remove(author);
        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book setBookCategory(String bookId, String categoryId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Category category = categoryService.findById(categoryId);
        List<Category> categories = book.getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            book.setCategories(categories);
            bookRepository.save(book);
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookCategory(String bookId, String categoryId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        Category category = categoryService.findById(categoryId);
        book.getCategories().remove(category);
        bookRepository.save(book);
        return book;
    }

    @Transactional(readOnly = true)
    public String getBookByIdToString(String bookId) {
        Book book = findById(bookId);
        return getBookToString(book);
    }

    @Transactional(readOnly = true)
    public String getAllBooksToString() {
        String res = new String();
        for (Book book :
                findAll()) {
            if (book != null) {
                res = res + String.format("%s \r\n", getBookToString(book));
            }
        }
        return res;
    }

    public String getBookToString(Book book) {
        if (book == null) {
            return "Book is null";
        } else {
            return String.format("%s | %s | %s | %s", book.getId(), book.getName(), getBookAutorToString(book), getBookCategoryToString(book));
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
                if (comment != null) {
                    res = res + String.format("%s | %s \r\n", comment.getId(), comment.getText());
                }
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
                if (author != null) {
                    authors = authors + author.getName() + "; ";
                }
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
                if (category != null) {
                    categories = categories + category.getName() + "; ";
                }
            }
        }
        return categories;
    }

    @Override
    public List<Author> getBookAuthorsByBookId(String bookId) {
        return bookRepository.getBookAuthorsByBookId(bookId);
    }

    @Override
    public List<Category> getBookCategoriesByBookId(String bookId) {
        return bookRepository.getBookCategoriesByBookId(bookId);
    }
}
