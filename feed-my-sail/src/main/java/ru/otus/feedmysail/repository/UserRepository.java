package ru.otus.feedmysail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Team;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findById(long id);

    List<AppUser> findByTeams(Team team);

    Optional<AppUser> findByUserName(String userName);
}
