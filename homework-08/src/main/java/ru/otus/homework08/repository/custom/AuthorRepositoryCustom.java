package ru.otus.homework08.repository.custom;

public interface AuthorRepositoryCustom {
    void removeBooksArrayElementsById(String id);

    long getBooksArrayLengthByAuthorId(String authorId);
}
