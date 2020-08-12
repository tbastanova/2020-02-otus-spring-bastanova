package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.feedmysail.exception.NoTeamFoundException;
import ru.otus.feedmysail.exception.NoUserFoundException;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.ProductResult;
import ru.otus.feedmysail.model.Team;
import ru.otus.feedmysail.repository.TeamRepositoryJpa;
import ru.otus.feedmysail.repository.UserRepositoryJpa;
import ru.otus.feedmysail.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepositoryJpa teamRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;

    public TeamServiceImpl(TeamRepositoryJpa teamRepositoryJpa, UserRepositoryJpa userRepositoryJpa) {
        this.teamRepositoryJpa = teamRepositoryJpa;
        this.userRepositoryJpa = userRepositoryJpa;
    }


    @Override
    public Team findById(long id) {
        return teamRepositoryJpa.findById(id).orElseThrow(() -> new NoTeamFoundException(new Throwable()));
    }

    @Override
    public List<Team> findByUserId(long userId) {
        AppUser user = userRepositoryJpa.findById(userId).orElseThrow(() -> new NoUserFoundException(new Throwable()));
        List<Team> teams = teamRepositoryJpa.findByUsers(user);
        return teams;
    }

    @Override
    public long count() {
        return teamRepositoryJpa.count();
    }

    @Override
    public List<ProductResult> getProductAvgByTeamId(long teamId) {
        return teamRepositoryJpa.getProductAvgByTeamId(teamId);
    }
}
