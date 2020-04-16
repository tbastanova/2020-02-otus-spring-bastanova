package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Category;

import java.util.List;

public interface CategoryDao {
    long insert(Category category);

    Category getById(long id);

    int update(Category category);

    void deleteById(long id);

    long count();

    List<Category> getAll();

    List<Category> findAllUsed();
}
