package ru.otus.homework11.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Category;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.service.BookService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Book> save(Book book) {
        Mono<Book> monoBook;
        if (book.getId() != null) {
            monoBook = findById(book.getId())
                    .flatMap(repBook -> updateRepositoryBook(repBook, book));
        } else {
            book.setAuthors(new ArrayList<>());
            book.setCategories(new ArrayList<>());
            monoBook = bookRepository.save(book);
        }
        return monoBook;
    }

    private Mono<Book> updateRepositoryBook(Book repBook, Book book) {
        if (book.getAuthors() == null || book.getAuthors().size() == 0) {
            book.setAuthors(repBook.getAuthors());
        }
        if (book.getCategories() == null || book.getCategories().size() == 0) {
            book.setCategories(repBook.getCategories());
        }
        return bookRepository.save(book);
    }

    @Transactional
    public Mono<Void> deleteById(String bookId) {
        return bookRepository.deleteById(bookId);
    }

    @Override
    public Mono<Long> count() {
        return bookRepository.count();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    @Transactional
    public Mono<Book> setBookAuthor(Book book, Author author) {

        List<Author> authors = book.getAuthors();
        if (!authors.contains(author)) {
            authors.add(author);
            book.setAuthors(authors);
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Mono<Book> removeBookAuthor(Book book, String authorId) {
        book.getAuthors().removeIf(author -> author.getId().equals(authorId));
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Mono<Book> setBookCategory(Book book, Category category) {
        List<Category> categories = book.getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            book.setCategories(categories);
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Mono<Book> removeBookCategory(Book book, String categoryId) {
        book.getCategories().removeIf(category -> category.getId().equals(categoryId));
        return bookRepository.save(book);
    }
}
