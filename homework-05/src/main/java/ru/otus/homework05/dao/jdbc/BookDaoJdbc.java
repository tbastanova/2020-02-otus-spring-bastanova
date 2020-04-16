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
import ru.otus.homework05.dao.ext.BookAuthorRelation;
import ru.otus.homework05.dao.ext.BookCategoryRelation;
import ru.otus.homework05.dao.mapper.AuthorMapper;
import ru.otus.homework05.dao.mapper.BookResultSetExtractor;
import ru.otus.homework05.dao.mapper.CategoryMapper;
import ru.otus.homework05.domain.Author;
import ru.otus.homework05.domain.Book;
import ru.otus.homework05.domain.Category;
import ru.otus.homework05.exception.NoAuthorFoundException;
import ru.otus.homework05.exception.NoBookFoundException;
import ru.otus.homework05.exception.NoCategoryFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorDao = new AuthorDaoJdbc(jdbcTemplate);
        this.categoryDao = new CategoryDaoJdbc(jdbcTemplate);
    }

    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int i = jdbcTemplate.update("insert into book (name) values (:name)", new MapSqlParameterSource().addValue("name", book.getName()), keyHolder, new String[]{"id"});
        return (long) keyHolder.getKey();
    }

    public Book getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            Book book = jdbcTemplate.query(
                    "select b.id, b.name from book b where id = :id", params, new BookResultSetExtractor()
            ).get(id);

            if (book != null) {
                book.setAuthors(getBookAuthor(id));
                book.setCategories(getBookCategory(id));
            }
            return book;
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
            List<Author> authors = authorDao.findAllUsed();
            List<BookAuthorRelation> authorRelations = getAllAuthorRelations();
            List<Category> categories = categoryDao.findAllUsed();
            List<BookCategoryRelation> categoryRelations = getAllCategoryRelations();

            Map<Long, Book> bookMap = jdbcTemplate.query("select b.id, b.name from book b", new BookResultSetExtractor());
            mergeBookAuthorInfo(bookMap, authors, authorRelations);
            mergeBookCategoryInfo(bookMap, categories, categoryRelations);
            return new ArrayList<>(Objects.requireNonNull(bookMap).values());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<BookAuthorRelation> getAllAuthorRelations() {
        return jdbcTemplate.query("select book_id, author_id from book_author order by book_id, author_id",
                (rs, i) -> new BookAuthorRelation(rs.getLong("book_id"), rs.getLong("author_id")));
    }

    private void mergeBookAuthorInfo(Map<Long, Book> books, List<Author> authors, List<BookAuthorRelation> relations) {
        Map<Long, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getId, c -> c));
        for (BookAuthorRelation r :
                relations) {
            if (books.containsKey(r.getBookId()) && authorMap.containsKey(r.getAuthorId())) {
                books.get(r.getBookId()).getAuthors().add(authorMap.get(r.getAuthorId()));
            }
        }
    }

    private List<BookCategoryRelation> getAllCategoryRelations() {
        return jdbcTemplate.query("select book_id, category_id from book_category order by book_id, category_id",
                (rs, i) -> new BookCategoryRelation(rs.getLong("book_id"), rs.getLong("category_id")));
    }

    private void mergeBookCategoryInfo(Map<Long, Book> books, List<Category> categories, List<BookCategoryRelation> relations) {
        Map<Long, Category> categoryMap = categories.stream().collect(Collectors.toMap(Category::getId, c -> c));
        for (BookCategoryRelation r :
                relations) {
            if (books.containsKey(r.getBookId()) && categoryMap.containsKey(r.getCategoryId())) {
                books.get(r.getBookId()).getCategories().add(categoryMap.get(r.getCategoryId()));
            }
        }
    }

    public int addBookAuthor(long bookId, long authorId) {
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

    public int addBookCategory(long bookId, long categoryId) {
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
