package ru.otus.feedmysail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findById(long id);

    List<Team> findByUsers(AppUser user);
}
