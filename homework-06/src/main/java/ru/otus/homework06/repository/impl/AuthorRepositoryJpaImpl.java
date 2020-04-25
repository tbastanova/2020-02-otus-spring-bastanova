package ru.otus.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.exception.NoAuthorFoundException;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.repository.AuthorRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public long insert(Author author) {
        author.setId(0);
        em.persist(author);
        em.flush();
        return author.getId();
    }

    @Override
    public void update(Author author) {
        Query query = em.createQuery("update Author a " +
                "set a.name = :name " +
                "where a.id = :id");
        query.setParameter("name", author.getName());
        query.setParameter("id", author.getId());
        if (query.executeUpdate() == 0) {
            throw new NoAuthorFoundException(new Throwable());
        } else {
            em.flush();
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Author a " +
                "where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        em.flush();
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }

    @Override
    public boolean checkExists(long id) {
        Query query = em.createQuery("select count(id) from Author a where a.id = :id", Long.class);
        query.setParameter("id", id);
        Long count = (Long) query.getSingleResult();
        return count == 1;
    }

    @Override
    public List<Author> getAuthorsByBookId(long bookId) {
        Query query = em.createNativeQuery("select * from Author a, book_author ba where a.id=ba.author_id and ba.book_id = :book_id", Author.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

}
