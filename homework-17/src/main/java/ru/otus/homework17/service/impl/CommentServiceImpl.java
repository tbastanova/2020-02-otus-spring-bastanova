package ru.otus.homework17.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework17.model.Book;
import ru.otus.homework17.model.Comment;
import ru.otus.homework17.repository.CommentRepository;
import ru.otus.homework17.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public Mono<Comment> save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Flux<Comment> findByBook_Id(String bookId) {
        return commentRepository.findByBook_Id(bookId);
    }

    @Transactional
    public Mono<Comment> addBookComment(Book book, Comment comment) {
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(String id) {
        return commentRepository.deleteById(id);
    }
}
