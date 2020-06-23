package ru.otus.homework14.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class BookMongo {
    @Id
    private String id;
    private String name;
    @DBRef(lazy = true)
    private List<AuthorMongo> authors;
    @DBRef(lazy = true)
    private List<CategoryMongo> categories;
}
