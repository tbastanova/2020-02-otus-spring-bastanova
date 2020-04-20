package ru.otus.homework05.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.dao.BookDao;
import ru.otus.homework05.dao.CategoryDao;
import ru.otus.homework05.dao.ext.BookRelation;
import ru.otus.homework05.dao.mapper.AuthorMapper;
import ru.otus.homework05.dao.mapper.BookMapper;
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
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private CategoryDao categoryDao;

    private static final Map<String, Object> authorRelationParam = Map.of(
            "childTable", "author",
            "relationTable", "book_author",
            "parentField", "book_id",
            "childField", "author_id",
            "mapper", new AuthorMapper()
    );

    private static final Map<String, Object> categoryRelationParam = Map.of(
            "childTable", "category",
            "relationTable", "book_category",
            "parentField", "book_id",
            "childField", "category_id",
            "mapper", new CategoryMapper()
    );

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int i = jdbcTemplate.update("insert into book (name) values (:name)", new MapSqlParameterSource().addValue("name", book.getName()), keyHolder, new String[]{"id"});
        return (long) keyHolder.getKey();
    }

    @Override
    public Book getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            Book book = jdbcTemplate.queryForObject(
                    "select b.id, b.name from book b where id = :id", params, new BookMapper()
            );

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

    @Override
    public int update(Book book) {
        if (!checkExists(book.getId())) {
            throw new NoBookFoundException(new Throwable());
        }

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", book.getName());
        mapSqlParameterSource.addValue("id", book.getId());
        return jdbcTemplate.update("update book set name=:name where id=:id", mapSqlParameterSource);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(
                "delete from book where id = :id", params
        );
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from book", new MapSqlParameterSource().addValue("id", 1), Integer.class);
    }

    @Override
    public List<Book> getAll() {
        try {
            List<Author> authors = authorDao.findAllUsed();
            List<BookRelation> authorRelations = getAllChildRelations(authorRelationParam);
            List<Category> categories = categoryDao.findAllUsed();
            List<BookRelation> categoryRelations = getAllChildRelations(categoryRelationParam);

            List<Book> books = jdbcTemplate.query("select b.id, b.name from book b", new BookMapper());
            Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, c -> c));
            mergeBookAuthorInfo(bookMap, authors, authorRelations);
            mergeBookCategoryInfo(bookMap, categories, categoryRelations);
            return new ArrayList<>(Objects.requireNonNull(bookMap).values());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private void mergeBookAuthorInfo(Map<Long, Book> books, List<Author> authors, List<BookRelation> relations) {
        Map<Long, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getId, c -> c));
        for (BookRelation r :
                relations) {
            if (books.containsKey(r.getBookId()) && authorMap.containsKey(r.getChildId())) {
                books.get(r.getBookId()).getAuthors().add(authorMap.get(r.getChildId()));
            }
        }
    }

    private void mergeBookCategoryInfo(Map<Long, Book> books, List<Category> categories, List<BookRelation> relations) {
        Map<Long, Category> categoryMap = categories.stream().collect(Collectors.toMap(Category::getId, c -> c));
        for (BookRelation r :
                relations) {
            if (books.containsKey(r.getBookId()) && categoryMap.containsKey(r.getChildId())) {
                books.get(r.getBookId()).getCategories().add(categoryMap.get(r.getChildId()));
            }
        }
    }

    @Override
    public int addBookAuthor(long bookId, long authorId) {
        if (!checkExists(bookId)) {
            throw new NoBookFoundException(new Throwable());
        }

        if (!authorDao.checkExists(authorId)) {
            throw new NoAuthorFoundException(new Throwable());
        }

        return addBookChild(authorRelationParam, bookId, authorId);
    }

    @Override
    public List<Author> getBookAuthor(long bookId) {
        return (List<Author>) this.getBookChild(authorRelationParam, bookId);
    }

    @Override
    public int addBookCategory(long bookId, long categoryId) {
        if (!checkExists(bookId)) {
            throw new NoBookFoundException(new Throwable());
        }

        CategoryDao categoryDao = new CategoryDaoJdbc(jdbcTemplate);
        if (!categoryDao.checkExists(categoryId)) {
            throw new NoCategoryFoundException(new Throwable());
        }

        return addBookChild(categoryRelationParam, bookId, categoryId);
    }

    @Override
    public List<Category> getBookCategory(long bookId) {
        return (List<Category>) this.getBookChild(categoryRelationParam, bookId);
    }

    @Override
    public boolean checkExists(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        int count = jdbcTemplate.queryForObject(
                "select count(id) from book b where id = :id", params, Integer.class
        );
        return count == 1;
    }

    private List<BookRelation> getAllChildRelations(Map<String, Object> relationParam) {
        String sql = String.format("select %s, %s from %s order by %s, %s", relationParam.get("parentField"), relationParam.get("childField"), relationParam.get("relationTable"), relationParam.get("parentField"), relationParam.get("childField"));
        return jdbcTemplate.query(sql,
                (rs, i) -> new BookRelation(rs.getLong((String) relationParam.get("parentField")), rs.getLong((String) relationParam.get("childField"))));
    }

    private List<?> getBookChild(Map<String, Object> relationParam, long bookId) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", bookId);
            String sql = String.format("select a.* from %s a, %s ba where a.id=ba.%s and ba.%s=:id", relationParam.get("childTable"), relationParam.get("relationTable"), relationParam.get("childField"), relationParam.get("parentField"));
            RowMapper mapper = (RowMapper) relationParam.get("mapper");
            return (List<?>) jdbcTemplate.query(sql, mapSqlParameterSource, mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private int addBookChild(Map<String, Object> relationParam, long bookId, long childId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("book_id", bookId);
        mapSqlParameterSource.addValue("child_id", childId);
        String sqlCheckExists = String.format("select count(*) from %s where %s=:book_id and %s=:child_id", relationParam.get("relationTable"), relationParam.get("parentField"), relationParam.get("childField"));
        int checkExists = jdbcTemplate.queryForObject(sqlCheckExists, mapSqlParameterSource, Integer.class);

        String sqlUpdate = String.format("insert into %s (%s,%s) values (:book_id,:child_id)", relationParam.get("relationTable"), relationParam.get("parentField"), relationParam.get("childField"));
        return checkExists < 1 ? jdbcTemplate.update(sqlUpdate, mapSqlParameterSource) : 1;
    }

}
