package ru.otus.homework08.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.exception.NoCommentFoundException;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.repository.CommentRepository;
import ru.otus.homework08.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new NoCommentFoundException(new Throwable()));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByBook_Id(String bookId) {
        return commentRepository.findByBook_Id(bookId);
    }

    @Transactional
    public Comment addBookComment(Book book, String commentText) {
        Comment comment = new Comment(commentText);
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Transactional
    public void updateBookId(String commentId, Book book) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoCommentFoundException(new Throwable()));
        comment.setBook(book);
        save(comment);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
