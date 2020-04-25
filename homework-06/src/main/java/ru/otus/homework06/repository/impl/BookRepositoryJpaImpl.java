package ru.otus.homework06.repository.impl;

import org.springframework.stereotype.Repository;
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
import java.util.List;
import java.util.Optional;

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
        em.flush();
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
        } else {
            em.flush();
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        em.flush();
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Book a", Long.class).getSingleResult();
    }

    @Override
    public boolean checkExists(long id) {
        Query query = em.createQuery("select count(id) from Book a where a.id = :id", Long.class);
        query.setParameter("id", id);
        Long count = (Long) query.getSingleResult();
        return count == 1;
    }

    @Override
    public void addBookAuthor(long bookId, long authorId) {
        Optional<Book> book = this.findById(bookId);
        Author author = em.find(Author.class, authorId);

        if (author == null) {
            throw new NoAuthorFoundException(new Throwable());
        }

        List<Author> authors = book.map(Book::getAuthors).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        if (!authors.contains(author)) {
            authors.add(author);
            em.flush();
        }
    }

    @Override
    public void addBookCategory(long bookId, long categoryId) {
        Optional<Book> book = this.findById(bookId);
        Category category = em.find(Category.class, categoryId);

        if (category == null) {
            throw new NoCategoryFoundException(new Throwable());
        }

        List<Category> categories = book.map(Book::getCategories).orElseThrow(() -> new NoBookFoundException(new Throwable()));
        if (!categories.contains(category)) {
            categories.add(category);
            em.flush();
        }
    }
}
