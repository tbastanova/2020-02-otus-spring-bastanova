package ru.otus.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoAuthorFoundException;
import ru.otus.homework06.exception.NoBookFoundException;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Category;
import ru.otus.homework06.repository.BookRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public long insert(Book book) {
        book.setId(0);
        em.persist(book);
        return book.getId();
    }

    @Override
    public void update(Book book) {
        Query query = em.createQuery("update Book b " +
                "set b.name = :name " +
                "where b.id = :id");
        query.setParameter("name", book.getName());
        query.setParameter("id", book.getId());
        if (query.executeUpdate() == 0) {
            throw new NoBookFoundException(new Throwable());
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long count() {
        BigInteger count = (BigInteger) em.createNativeQuery("select count(id) from Book a").getSingleResult();
        return count.longValue();
    }

    @Override
    public boolean checkExists(long id) {
        Query query = em.createNativeQuery("select count(id) from Book a where a.id = :id");
        query.setParameter("id", id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue() == 1;
    }

    @Override
    public void addBookAuthor(long bookId, long authorId) {
        Optional<Book> book = this.findById(bookId);
        Author author = em.find(Author.class, authorId);

        if (book.isEmpty()) {
            throw new NoBookFoundException(new Throwable());
        }

        if (author == null) {
            throw new NoAuthorFoundException(new Throwable());
        }

        List<Author> authors = book.get().getAuthors();
        if (!authors.contains(author)) {
            authors.add(author);
        }
    }

    @Override
    public List<Author> getBookAuthor(long bookId) {
        return findById(bookId).get().getAuthors();
    }

    @Override
    public void addBookCategory(long bookId, long categoryId) {
        Optional<Book> book = this.findById(bookId);
        Category category = em.find(Category.class, categoryId);

        if (book.isEmpty()) {
            throw new NoBookFoundException(new Throwable());
        }

        if (category == null) {
            throw new NoCategoryFoundException(new Throwable());
        }

        List<Category> categories = book.get().getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    @Override
    public List<Category> getBookCategory(long bookId) {

        return findById(bookId).get().getCategories();
    }
}
