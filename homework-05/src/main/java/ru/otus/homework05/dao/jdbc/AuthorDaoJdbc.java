package ru.otus.homework05.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.dao.mapper.AuthorMapper;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.exception.NoAuthorFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int i = jdbcTemplate.update("insert into author (name) values (:name)", new MapSqlParameterSource().addValue("name", author.getName()), keyHolder, new String[]{"id"});
        return (long) keyHolder.getKey();
    }

    @Override
    public int update(Author author) {
        if (this.getById(author.getId()) == null) {
            throw new NoAuthorFoundException(new Throwable());
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", author.getName());
        mapSqlParameterSource.addValue("id", author.getId());
        return jdbcTemplate.update("update author set name=:name where id=:id", mapSqlParameterSource);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from author", new MapSqlParameterSource().addValue("id", 1), Integer.class);
    }

    @Override
    public Author getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return jdbcTemplate.queryForObject(
                    "select * from author where id = :id", params, new AuthorMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        try {
            return jdbcTemplate.query("select * from author", new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(
                "delete from author where id = :id", params
        );
    }

    @Override
    public List<Author> findAllUsed() {
        return jdbcTemplate.query("select a.id, a.name from author a inner join book_author ba on a.id = ba.author_id group by a.id, a.name order by a.name", new AuthorMapper());
    }

    @Override
    public boolean checkExists(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        int count = jdbcTemplate.queryForObject(
                "select count(id) from author a where id = :id", params, Integer.class
        );
        return count == 1;
    }
}
