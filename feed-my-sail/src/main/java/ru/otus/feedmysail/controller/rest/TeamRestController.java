package ru.otus.feedmysail.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.feedmysail.controller.rest.dto.TeamDto;
import ru.otus.feedmysail.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeamRestController {

    private final TeamService teamService;

    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/user/{userId}/team")
    public List<TeamDto> getUserTeams(@PathVariable("userId") long userId) {
        return teamService.findByUserId(userId).stream().map(TeamDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/team/{id}")
    public TeamDto getTeam(@PathVariable("id") long id) {
        return TeamDto.toDto(teamService.findById(id));
    }

}
