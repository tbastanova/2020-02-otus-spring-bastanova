package ru.otus.homework06.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exception.NoCommentFoundException;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;
import ru.otus.homework06.repository.CommentRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select a from Comment a", Comment.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public long insert(Comment comment) {
        comment.setId(0);
        em.persist(comment);
        em.flush();
        return comment.getId();
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        Query query = em.createQuery("update Comment a " +
                "set a.text = :text " +
                "where a.id = :id");
        query.setParameter("text", comment.getText());
        query.setParameter("id", comment.getId());
        if (query.executeUpdate() == 0) {
            throw new NoCommentFoundException(new Throwable());
        } else {
            em.flush();
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Comment a " +
                "where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        em.flush();
    }

    @Override
    public boolean checkExists(long id) {
        Query query = em.createQuery("select count(id) from Comment a where a.id = :id", Long.class);
        query.setParameter("id", id);
        Long count = (Long) query.getSingleResult();
        return count == 1;
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        Query query = em.createQuery("select a from Comment a inner join a.book b where b.id = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updateBookId(Comment comment, Book book) {
        comment.setBook(book);
        em.flush();
    }
}
