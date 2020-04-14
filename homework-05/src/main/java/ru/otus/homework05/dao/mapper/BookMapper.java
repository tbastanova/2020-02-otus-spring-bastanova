package ru.otus.homework05.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework05.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookMapper implements RowMapper<Book> {

    public Book mapRow(ResultSet resultSet, int i) throws SQLException {

        BookResultSetExtractor extractor = new BookResultSetExtractor();
        List<Book> books = (List<Book>) extractor.extractData(resultSet);
        return books.get(0);
    }
}
