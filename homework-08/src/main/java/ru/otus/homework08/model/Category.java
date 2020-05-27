package ru.otus.homework08.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "category")
public class Category {
    @Id
    private String id;
    private String name;

    @DBRef(lazy = true)
    private List<Book> books;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
        this.books = new ArrayList<>();
    }

    public Category(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
