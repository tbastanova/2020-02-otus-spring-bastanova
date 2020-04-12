package ru.otus.homework05.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.dao.CategoryDao;
import ru.otus.homework05.dao.mapper.CategoryMapper;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.exception.NoCategoryFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryDaoJdbc implements CategoryDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int i = jdbcTemplate.update("insert into category (name) values (:name)", new MapSqlParameterSource().addValue("name", category.getName()), keyHolder, new String[]{"id"});
        return (long) keyHolder.getKey();
    }

    public int update(Category category) {
        if (this.getById(category.getId()) == null) {
            throw new NoCategoryFoundException(new Throwable());
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", category.getName());
        mapSqlParameterSource.addValue("id", category.getId());
        return jdbcTemplate.update("update category set name=:name where id=:id", mapSqlParameterSource);
    }

    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from category", new MapSqlParameterSource().addValue("id", 1), Integer.class);
    }

    public Category getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return jdbcTemplate.queryForObject(
                    "select * from category where id = :id", params, new CategoryMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Category> getAll() {
        try {
            return jdbcTemplate.query("select * from category", new CategoryMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(
                "delete from category where id = :id", params
        );
    }
}