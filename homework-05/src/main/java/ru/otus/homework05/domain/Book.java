package ru.otus.homework05.domain;

import java.util.List;
import java.util.Objects;

public class Book {

    private final long id;
    private final String name;
    private List<Author> authors;
    private List<Category> categories;

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
