package ru.otus.feedmysail.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.feedmysail.controller.rest.dto.UserDto;
import ru.otus.feedmysail.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/team/{teamId}/users")
    public List<UserDto> getTeamUsers(@PathVariable("teamId") long teamId) {
        return userService.findByTeamId(teamId).stream().map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable("id") long id) {
        return UserDto.toDto(userService.findById(id));
    }

}
