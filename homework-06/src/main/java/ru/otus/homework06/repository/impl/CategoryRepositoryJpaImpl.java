package ru.otus.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.exception.NoCategoryFoundException;
import ru.otus.homework06.model.Category;
import ru.otus.homework06.repository.CategoryRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
        em.flush();
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
        } else {
            em.flush();
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Category a " +
                "where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        em.flush();
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Category a", Long.class).getSingleResult();
    }

    @Override
    public boolean checkExists(long id) {
        Query query = em.createQuery("select count(id) from Category a where a.id = :id", Long.class);
        query.setParameter("id", id);
        Long count = (Long) query.getSingleResult();
        return count == 1;
    }

    @Override
    public List<Category> getCategoriesByBookId(long bookId) {
        Query query = em.createNativeQuery("select * from category a, book_category ba where a.id=ba.category_id and ba.book_id = :book_id", Category.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }
}
