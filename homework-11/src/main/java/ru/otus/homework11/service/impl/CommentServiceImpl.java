package ru.otus.homework11.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.repository.CommentRepository;
import ru.otus.homework11.service.CommentService;

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
    public Comment save(Comment comment) {
        commentRepository.save(comment).subscribe();
        return comment;
    }

    @Override
    public Flux<Comment> findByBook_Id(String bookId) {
        return commentRepository.findByBook_Id(bookId);
    }

    @Transactional
    public Comment addBookComment(Book book, Comment comment) {
        comment.setBook(book);
        commentRepository.save(comment).subscribe();
        return comment;
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id).subscribe();
    }
}
