package ru.otus.homework16.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private List<Book> books;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
        this.books = new ArrayList<>();
    }
}
