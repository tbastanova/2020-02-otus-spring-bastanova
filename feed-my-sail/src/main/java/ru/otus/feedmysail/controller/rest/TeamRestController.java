package ru.otus.feedmysail.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.feedmysail.controller.rest.dto.ProductResultDto;
import ru.otus.feedmysail.controller.rest.dto.TeamDto;
import ru.otus.feedmysail.controller.rest.dto.UserDto;
import ru.otus.feedmysail.service.TeamService;
import ru.otus.feedmysail.service.UserProductService;
import ru.otus.feedmysail.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeamRestController {

    private final TeamService teamService;
    private final UserService userService;
    private final UserProductService userProductService;

    public TeamRestController(TeamService teamService, UserService userService, UserProductService userProductService) {
        this.teamService = teamService;
        this.userService = userService;
        this.userProductService = userProductService;
    }

    @GetMapping("/team/{teamId}/user")
    public List<UserDto> getTeamUsers(@PathVariable("teamId") long teamId) {
        return userService.findByTeamId(teamId).stream().map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/team/{id}")
    public TeamDto getTeam(@PathVariable("id") long id) {
        return TeamDto.toDto(teamService.findById(id));
    }

    @GetMapping("/teamProductResult/{teamId}")
    public List<ProductResultDto> getTeamProductResult(@PathVariable("teamId") long teamId) {
        return userProductService.getProductAvgByTeamId(teamId).stream().map(ProductResultDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/teamFilteredProductResult/{teamId}")
    public List<ProductResultDto> getFilteredProductResult(@PathVariable("teamId") long teamId) {
        return userProductService.getFilteredProductAvgByTeamId(teamId, 2).stream().map(ProductResultDto::toDto)
                .collect(Collectors.toList());
    }
}
