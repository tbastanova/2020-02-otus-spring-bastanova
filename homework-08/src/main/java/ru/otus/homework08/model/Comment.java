package ru.otus.homework08.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;
    private String text;

    @DBRef
    private Book book;

    public Comment(String id, String text) {
        this.id = id;
        this.text = text;
        this.book = null;
    }

    public Comment(String text) {
        this.text = text;
        this.book = null;
    }
}
