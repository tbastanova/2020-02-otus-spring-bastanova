package ru.otus.homework08.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.exception.NoAuthorFoundException;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new NoAuthorFoundException(new Throwable()));
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    public long count() {
        return authorRepository.count();
    }

    @Override
    public boolean existsById(String id) {
        return authorRepository.existsById(id);
    }
}
