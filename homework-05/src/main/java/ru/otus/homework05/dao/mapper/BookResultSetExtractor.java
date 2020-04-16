package ru.otus.homework05.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.homework05.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {

    @Override
    public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        Map<Long, Book> books = new HashMap<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            Book book = books.get(id);
            if (book == null) {
                book = new Book(id, resultSet.getString("name"));
                book.setAuthors(new ArrayList<>());
                book.setCategories(new ArrayList<>());
                books.put(book.getId(), book);
            }
        }
        return books;
    }
}
