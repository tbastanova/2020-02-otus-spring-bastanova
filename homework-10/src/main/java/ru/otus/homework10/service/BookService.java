package ru.otus.homework10.service;


import org.springframework.ui.Model;
import ru.otus.homework10.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(long id);

    Book save(Book book);

    void deleteById(long bookId);

    Book setBookAuthor(long bookId, long authorId);

    Book removeBookAuthor(long bookId, long authorId);

    Book setBookCategory(long bookId, long categoryId);

    Book removeBookCategory(long bookId, long authorId);

    Model getEditBookModel(long id, Model model);

}
