package ru.otus.homework05.dao.ext;

import lombok.Data;

@Data
public class BookAuthorRelation {
    private final long bookId;
    private final long authorId;
}
