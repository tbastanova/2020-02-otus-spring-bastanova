package ru.otus.homework05.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework05.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookMapper implements RowMapper<Book> {
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Book book = new Book(id, name);
        book.setAuthors(new ArrayList<>());
        book.setCategories(new ArrayList<>());
        return book;
    }
}