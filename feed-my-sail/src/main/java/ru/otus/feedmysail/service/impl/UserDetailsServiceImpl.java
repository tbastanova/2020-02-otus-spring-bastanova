package ru.otus.feedmysail.service.impl;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.otus.feedmysail.model.AppUser;
import ru.otus.feedmysail.model.Role;
import ru.otus.feedmysail.service.UserService;


import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private long userId;
//    private String userFullName;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
        this.userId = -1;
//        this.userFullName="";
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        AppUser user = userService.findByUserName(login);
        this.userId = user.getId();
//        this.userFullName=user.getLastName()+" "+user.getFirstName()+" "+user.getMiddleName();

        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role :
                user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetails userDetails =
                new User(user.getUserName(),
                        user.getPassword(),
                        roles);

        return userDetails;
    }


    public long getUserId() {
        return (userId==-1? getPrincipalUser().getId():userId);
    }

//    public String getUserFullName() {
//        if(userId==-1) {
//            AppUser user = getPrincipalUser();
//            userFullName = user.getLastName()+" "+user.getFirstName()+" "+user.getMiddleName();
//        }
//        return userFullName;
//    }

    private AppUser getPrincipalUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        AppUser user = userService.findByUserName(username);
        this.userId=user.getId();
//        this.userFullName=user.getLastName()+" "+user.getFirstName()+" "+user.getMiddleName();
        return user;
    }

}
