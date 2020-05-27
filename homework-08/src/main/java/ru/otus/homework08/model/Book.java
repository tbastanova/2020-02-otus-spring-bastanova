package ru.otus.homework08.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    private String name;
    @DBRef(lazy = true)
    private List<Author> authors;
    @DBRef(lazy = true)
    private List<Category> categories;

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public Book(String name) {
        this.name = name;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
