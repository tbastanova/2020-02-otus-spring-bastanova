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
    public Book save(Book book) {
        if (book.getId() != null) {
            findById(book.getId()).subscribe(repBook -> updateRepositoryBook(repBook, book));
        } else {
            book.setAuthors(new ArrayList<>());
            book.setCategories(new ArrayList<>());
            bookRepository.save(book).subscribe();
        }
        return book;
    }

    private void updateRepositoryBook(Book repBook, Book book) {
        if (book.getAuthors() == null || book.getAuthors().size() == 0) {
            book.setAuthors(repBook.getAuthors());
        }
        if (book.getCategories() == null || book.getCategories().size() == 0) {
            book.setCategories(repBook.getCategories());
        }
        bookRepository.save(book).subscribe();
    }

    @Transactional
    public void deleteById(String bookId) {
        bookRepository.deleteById(bookId).subscribe();
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
    public Book setBookAuthor(Book book, Author author) {

        List<Author> authors = book.getAuthors();
        if (!authors.contains(author)) {
            authors.add(author);
            book.setAuthors(authors);
            bookRepository.save(book).subscribe();
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookAuthor(Book book, String authorId) {
        book.getAuthors().removeIf(author -> author.getId().equals(authorId));
        bookRepository.save(book).subscribe();
        return book;
    }

    @Override
    @Transactional
    public Book setBookCategory(Book book, Category category) {
        List<Category> categories = book.getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            book.setCategories(categories);
            bookRepository.save(book).subscribe();
        }
        return book;
    }

    @Override
    @Transactional
    public Book removeBookCategory(Book book, String categoryId) {
        book.getCategories().removeIf(category -> category.getId().equals(categoryId));
        bookRepository.save(book).subscribe();
        return book;
    }
}
