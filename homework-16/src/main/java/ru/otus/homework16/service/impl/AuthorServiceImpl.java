package ru.otus.homework16.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework16.exception.NoAuthorFoundException;
import ru.otus.homework16.model.Author;
import ru.otus.homework16.repository.AuthorRepositoryJpa;
import ru.otus.homework16.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepositoryJpa authorRepositoryJpa;

    public AuthorServiceImpl(AuthorRepositoryJpa authorRepositoryJpa) {
        this.authorRepositoryJpa = authorRepositoryJpa;
    }

    @Override
    public List<Author> findAll() {
        return authorRepositoryJpa.findAll();
    }

    @Override
    public Author findById(long id) {
        return authorRepositoryJpa.findById(id).orElseThrow(() -> new NoAuthorFoundException(new Throwable()));
    }

    @Override
    public Author save(Author author) {
        return authorRepositoryJpa.save(author);
    }

}
