package ru.otus.homework05.dao.mapper;

import org.h2.jdbc.JdbcSQLNonTransientException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookResultSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Book> books = new ArrayList<>();

        if (resultSet.getRow() == 0) {
            resultSet.next();
        }
        try {
            Book book = new Book(resultSet.getLong("id"), resultSet.getString("name"));
            do {
                Book currentBook = new Book(resultSet.getLong("id"), resultSet.getString("name"));
                if (!currentBook.equals(book)) {
                    book = currentBook;
                }
                if (resultSet.getLong("author_id") > 0) {
                    List<Author> authors = book.getAuthors();
                    if (authors == null) {
                        authors = new ArrayList<>();
                    }
                    Author author = new Author(resultSet.getLong("author_id"), resultSet.getString("author_name"));
                    if (!authors.contains(author)) {
                        authors.add(author);
                    }
                    book.setAuthors(authors);
                }
                if (resultSet.getLong("category_id") > 0) {
                    List<Category> categories = book.getCategories();
                    if (categories == null) {
                        categories = new ArrayList<>();
                    }
                    Category category = new Category(resultSet.getLong("category_id"), resultSet.getString("category_name"));
                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                    book.setCategories(categories);
                }
                if (!books.contains(book)) {
                    books.add(book);
                }
            }
            while (resultSet.next());
        } catch (JdbcSQLNonTransientException e) {
            return new ArrayList<>();
        }
        return books;
    }
}
