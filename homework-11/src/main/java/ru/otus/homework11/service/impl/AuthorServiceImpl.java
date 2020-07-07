package ru.otus.homework11.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author save(Author author) {
        authorRepository.save(author).subscribe();
        return author;
    }

    @Transactional
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Mono<Long> count() {
        return authorRepository.count();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return authorRepository.existsById(id);
    }
}
