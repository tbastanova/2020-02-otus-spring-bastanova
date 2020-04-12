package ru.otus.homework05.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.dao.BookDao;
import ru.otus.homework05.dao.CategoryDao;
import ru.otus.homework05.dao.mapper.AuthorMapper;
import ru.otus.homework05.dao.mapper.BookMapper;
import ru.otus.homework05.dao.mapper.CategoryMapper;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.exception.NoAuthorFoundException;
import ru.otus.homework05.exception.NoBookFoundException;
import ru.otus.homework05.exception.NoCategoryFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int i = jdbcTemplate.update("insert into book (name) values (:name)", new MapSqlParameterSource().addValue("name", book.getName()), keyHolder, new String[]{"id"});
        return (long) keyHolder.getKey();
    }

    public Book getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return jdbcTemplate.queryForObject(
                    "select * from book where id = :id", params, new BookMapper()
            );
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int update(Book book) {
        if (this.getById(book.getId()) == null) {
            throw new NoBookFoundException(new Throwable());
        }

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", book.getName());
        mapSqlParameterSource.addValue("id", book.getId());
        return jdbcTemplate.update("update book set name=:name where id=:id", mapSqlParameterSource);
    }


    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(
                "delete from book where id = :id", params
        );
    }

    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from book", new MapSqlParameterSource().addValue("id", 1), Integer.class);
    }

    public List<Book> getAll() {
        try {
            return jdbcTemplate.query("select * from book", new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int setBookAuthor(long bookId, long authorId) {
        if (this.getById(bookId) == null) {
            throw new NoBookFoundException(new Throwable());
        }

        AuthorDao authorDao = new AuthorDaoJdbc(jdbcTemplate);
        if (authorDao.getById(authorId) == null) {
            throw new NoAuthorFoundException(new Throwable());
        }

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("book_id", bookId);
        mapSqlParameterSource.addValue("author_id", authorId);
        int checkExists = jdbcTemplate.queryForObject("select count(*) from book_author where book_id=:book_id and author_id=:author_id", mapSqlParameterSource, Integer.class);

        return checkExists < 1 ? jdbcTemplate.update("insert into book_author (book_id,author_id) values (:book_id,:author_id)", mapSqlParameterSource) : 1;
    }

    public List<Author> getBookAuthor(long bookId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", bookId);
            return jdbcTemplate.query("select a.* from author a, book_author ba where a.id=ba.author_id and ba.book_id=:id", mapSqlParameterSource, new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int setBookCategory(long bookId, long categoryId) {
        if (this.getById(bookId) == null) {
            throw new NoBookFoundException(new Throwable());
        }

        CategoryDao categoryDao = new CategoryDaoJdbc(jdbcTemplate);
        if (categoryDao.getById(categoryId) == null) {
            throw new NoCategoryFoundException(new Throwable());
        }

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("book_id", bookId);
        mapSqlParameterSource.addValue("category_id", categoryId);
        int checkExists = jdbcTemplate.queryForObject("select count(*) from book_category where book_id=:book_id and category_id=:category_id", mapSqlParameterSource, Integer.class);
        return checkExists < 1 ? jdbcTemplate.update("insert into book_category (book_id,category_id) values (:book_id,:category_id)", mapSqlParameterSource) : 1;
    }

    public List<Category> getBookCategory(long bookId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", bookId);
            return jdbcTemplate.query("select a.* from category a, book_category ba where a.id=ba.category_id and ba.book_id=:id", mapSqlParameterSource, new CategoryMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
