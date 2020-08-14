package ru.otus.feedmysail.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.feedmysail.model.AppUser;

@Data
@AllArgsConstructor
public class UserDto {
    private long id = -1;
    private String shortName;
    private String fullName;

    public static UserDto toDto(AppUser user) {
        return new UserDto(user.getId(),
                user.getFirstName() + " " + user.getLastName().substring(0, 1) + ".",
                user.getLastName() + " " + user.getFirstName() + (user.getMiddleName() == null ? "" : " " + user.getMiddleName()));
    }

}
