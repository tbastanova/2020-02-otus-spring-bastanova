package ru.otus.homework05.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework05.domain.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Category(id, name);
    }
}
