package ru.otus.feedmysail.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.feedmysail.exception.NoTeamFoundException;
import ru.otus.feedmysail.exception.NoUserFoundException;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Team;
import ru.otus.feedmysail.repository.TeamRepository;
import ru.otus.feedmysail.repository.UserRepository;
import ru.otus.feedmysail.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Team findById(long id) {
        return teamRepository.findById(id).orElseThrow(() -> new NoTeamFoundException(new Throwable()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> findByUserId(long userId) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new NoUserFoundException(new Throwable()));
        List<Team> teams = teamRepository.findByUsers(user);
        return teams;
    }
}
