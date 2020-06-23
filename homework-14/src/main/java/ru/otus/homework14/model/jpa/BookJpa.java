package ru.otus.homework14.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class BookJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @ManyToMany(targetEntity = AuthorJpa.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<AuthorJpa> authors;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @ManyToMany(targetEntity = CategoryJpa.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryJpa> categories;


}
