package ru.otus.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoAuthorFoundException;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.repository.AuthorRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Transactional
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
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Author a " +
                "where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long count() {
        BigInteger count = (BigInteger) em.createNativeQuery("select count(id) from Author a").getSingleResult();
        return count.longValue();
    }

    @Override
    public boolean checkExists(long id) {
        Query query = em.createNativeQuery("select count(id) from Author a where a.id = :id");
        query.setParameter("id", id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue() == 1;
    }

}
