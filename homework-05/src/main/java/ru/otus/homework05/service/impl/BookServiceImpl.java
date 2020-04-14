package ru.otus.homework05.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework05.dao.BookDao;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public String getBookToString(long bookId) {
        Book book = bookDao.getById(bookId);

        String result = String.format("%d | %s | %s | %s", book.getId(), book.getName(), getBookAutorToString(book), getBookCategoryToString(book));
        return result;
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
