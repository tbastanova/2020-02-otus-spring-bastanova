package ru.otus.feedmysail.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.feedmysail.model.Team;

@Data
@AllArgsConstructor
public class TeamDto {
    private long id = -1;
    private String name;

    public static TeamDto toDto(Team team) {
        return new TeamDto(team.getId(), team.getName() + " (капитан " + team.getSkipper().getLastName() + ")");
    }

}
