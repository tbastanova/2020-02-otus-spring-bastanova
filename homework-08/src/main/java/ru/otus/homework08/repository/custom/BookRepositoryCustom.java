package ru.otus.homework08.repository.custom;

import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Category;

import java.util.List;

public interface BookRepositoryCustom {
    List<Author> getBookAuthorsByBookId(String bookId);

    List<Category> getBookCategoriesByBookId(String bookId);

    void removeAuthorsArrayElementsById(String id);

    void removeCategoriesArrayElementsById(String id);

    long getAuthorsArrayLengthByBookId(String bookId);

    long getCategoriesArrayLengthByBookId(String bookId);

}
