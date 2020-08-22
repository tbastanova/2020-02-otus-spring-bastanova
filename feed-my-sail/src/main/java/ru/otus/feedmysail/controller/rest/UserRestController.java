package ru.otus.feedmysail.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.feedmysail.controller.rest.dto.TeamDto;
import ru.otus.feedmysail.controller.rest.dto.UserDto;
import ru.otus.feedmysail.controller.rest.dto.UserProductDto;
import ru.otus.feedmysail.model.UserProduct;
import ru.otus.feedmysail.service.TeamService;
import ru.otus.feedmysail.service.UserProductService;
import ru.otus.feedmysail.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    private final UserService userService;
    private final UserProductService userProductService;
    private final TeamService teamService;

    public UserRestController(UserService userService, UserProductService userProductService, TeamService teamService) {
        this.userService = userService;
        this.userProductService = userProductService;
        this.teamService = teamService;
    }

    @GetMapping("/user/{userId}/team")
    public List<TeamDto> getUserTeams(@PathVariable("userId") long userId) {
        return teamService.findByUserId(userId).stream().map(TeamDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable("id") long id) {
        return UserDto.toDto(userService.findById(id));
    }

    @GetMapping("/user/{userId}/product")
    public List<UserProductDto> getUserProducts(@PathVariable("userId") long userId) {
        return userProductService.findByUserId(userId).stream().map(UserProductDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/user/{userId}/product")
    public void voteProduct(
            @PathVariable("userId") long userId,
            long productId,
            Integer vote
    ) {
        userProductService.save(new UserProduct(0, userId, productId, vote));
    }
}
