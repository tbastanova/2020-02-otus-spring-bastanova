package ru.otus.feedmysail.service;

import ru.otus.feedmysail.model.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> findAll();

    AppUser findById(long id);

    List<AppUser> findByTeamId(long teamId);

    AppUser findByUserName(String userName);
}
