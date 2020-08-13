package ru.otus.feedmysail.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Team;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryJpa extends CrudRepository<AppUser, Long> {

    List<AppUser> findAll();

    Optional<AppUser> findById(long id);

    List<AppUser> findByTeams(Team team);

    Optional<AppUser> findByUserName(String userName);
}
