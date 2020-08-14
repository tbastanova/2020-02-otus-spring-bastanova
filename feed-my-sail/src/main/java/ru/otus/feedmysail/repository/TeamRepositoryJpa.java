package ru.otus.feedmysail.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepositoryJpa extends CrudRepository<Team, Long> {
    List<Team> findAll();

    Optional<Team> findById(long id);

    List<Team> findByUsers(AppUser user);
}
