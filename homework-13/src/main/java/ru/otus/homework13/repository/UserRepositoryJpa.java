package ru.otus.homework13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework13.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryJpa extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findByUserName(String userName);

    List<AppUser> findAll();

}
