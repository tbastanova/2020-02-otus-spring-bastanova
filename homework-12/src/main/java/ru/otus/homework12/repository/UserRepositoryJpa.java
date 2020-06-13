package ru.otus.homework12.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework12.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryJpa extends CrudRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    List<User> findAll();

}
