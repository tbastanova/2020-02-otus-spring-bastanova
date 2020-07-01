package ru.otus.homework16.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.homework16.model.Comment;

@Data
@AllArgsConstructor
public class CommentDto {
    private long id = -1;
    private String text;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }

}
