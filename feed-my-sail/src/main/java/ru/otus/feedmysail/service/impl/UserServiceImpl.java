package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.feedmysail.exception.NoTeamFoundException;
import ru.otus.feedmysail.exception.NoUserFoundException;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Team;
import ru.otus.feedmysail.repository.TeamRepositoryJpa;
import ru.otus.feedmysail.repository.UserRepositoryJpa;
import ru.otus.feedmysail.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryJpa userRepositoryJpa;
    private final TeamRepositoryJpa teamRepositoryJpa;

    public UserServiceImpl(UserRepositoryJpa userRepositoryJpa, TeamRepositoryJpa teamRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.teamRepositoryJpa = teamRepositoryJpa;
    }


    @Override
    public List<AppUser> findAll() {
        return userRepositoryJpa.findAll();
    }

    @Override
    public AppUser findById(long id) {
        return userRepositoryJpa.findById(id).orElseThrow(() -> new NoUserFoundException(new Throwable()));
    }

    @Override
    public List<AppUser> findByTeamId(long teamId) {
        Team team = teamRepositoryJpa.findById(teamId).orElseThrow(() -> new NoTeamFoundException(new Throwable()));
        List<AppUser> users = userRepositoryJpa.findByTeams(team);
        return users;
    }
}
