package ru.otus.homework12.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.homework12.model.Role;
import ru.otus.homework12.model.User;
import ru.otus.homework12.repository.UserRepositoryJpa;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositoryJpa userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.findByUserName(login).orElseThrow(() -> new UsernameNotFoundException(login));
        Set<GrantedAuthority> roles = new HashSet();
        for (Role role:
                user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getUserName(),
                        user.getPassword(),
                        roles);

        return userDetails;
    }
}
