package ru.otus.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.model.Category;
import ru.otus.homework06.repository.CategoryRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CategoryRepositoryJpaImpl implements CategoryRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Category> findById(long id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = em.createQuery("select a from Category a", Category.class);
        return query.getResultList();
    }

    @Override
    public long insert(Category category) {
        category.setId(0);
        em.persist(category);
        return category.getId();
    }

    @Override
    public void update(Category category) {
        Query query = em.createQuery("update Category a " +
                "set a.name = :name " +
                "where a.id = :id");
        query.setParameter("name", category.getName());
        query.setParameter("id", category.getId());
        if (query.executeUpdate() == 0) {
            throw new NoCategoryFoundException(new Throwable());
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Category a " +
                "where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long count() {
        BigInteger count = (BigInteger) em.createNativeQuery("select count(id) from Category a").getSingleResult();
        return count.longValue();
    }

    @Override
    public boolean checkExists(long id) {
        Query query = em.createNativeQuery("select count(id) from Category a where a.id = :id");
        query.setParameter("id", id);
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue() == 1;
    }
}
