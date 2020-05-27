package ru.otus.homework08.repository.custom;

public interface CategoryRepositoryCustom {
    void removeBooksArrayElementsById(String id);

    long getBooksArrayLengthByCategoryId(String categoryId);
}
