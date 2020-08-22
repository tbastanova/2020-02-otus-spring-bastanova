package ru.otus.feedmysail.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.feedmysail.exception.NoTeamFoundException;
import ru.otus.feedmysail.exception.NoUserFoundException;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Team;
import ru.otus.feedmysail.repository.TeamRepository;
import ru.otus.feedmysail.repository.UserRepository;
import ru.otus.feedmysail.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public UserServiceImpl(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoUserFoundException(new Throwable()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppUser> findByTeamId(long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new NoTeamFoundException(new Throwable()));
        List<AppUser> users = userRepository.findByTeams(team);
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
    }
}
