package ru.otus.homework08.repository.custom.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Category;
import ru.otus.homework08.repository.custom.BookRepositoryCustom;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Data
    private class ArraySizeProjection {
        private int size;
    }

    @Override
    public List<Author> getBookAuthorsByBookId(String bookId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("authors")
                , project().andExclude("_id").and(valueOfToArray("authors")).as("authors_map")
                , project().and("authors_map").arrayElementAt(1).as("authors_id_map")
                , project().and("authors_id_map.v").as("authors_id")
                , lookup("author", "authors_id", "_id", "authors")
                , project().and("authors._id").as("_id").and("authors.name").as("name")
        );

        return mongoTemplate.aggregate(aggregation, Book.class, Author.class).getMappedResults();
    }

    @Override
    public List<Category> getBookCategoriesByBookId(String bookId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("categories")
                , project().andExclude("_id").and(valueOfToArray("categories")).as("categories_map")
                , project().and("categories_map").arrayElementAt(1).as("categories_id_map")
                , project().and("categories_id_map.v").as("categories_id")
                , lookup("category", "categories_id", "_id", "categories")
                , project().and("categories._id").as("_id").and("categories.name").as("name")
        );

        return mongoTemplate.aggregate(aggregation, Book.class, Category.class).getMappedResults();
    }

    @Override
    public void removeAuthorsArrayElementsById(String id) {
        val query = Query.query(Criteria.where("$id").is(id));
        val update = new Update().pull("books", query);
        mongoTemplate.updateMulti(new Query(), update, "author");
    }

    @Override
    public void removeCategoriesArrayElementsById(String id) {
        val query = Query.query(Criteria.where("$id").is(id));
        val update = new Update().pull("books", query);
        mongoTemplate.updateMulti(new Query(), update, "category");
    }

    @Override
    public long getAuthorsArrayLengthByBookId(String bookId) {
        val aggregation = Aggregation.newAggregation(
                match(where("id").is(bookId)),
                project().andExclude("_id").and("authors").size().as("size"));

        val arraySizeProjection = mongoTemplate.aggregate(aggregation, Book.class, ArraySizeProjection.class).getUniqueMappedResult();
        return arraySizeProjection == null ? 0 : arraySizeProjection.getSize();
    }

    @Override
    public long getCategoriesArrayLengthByBookId(String bookId) {
        val aggregation = Aggregation.newAggregation(
                match(where("id").is(bookId)),
                project().andExclude("_id").and("categories").size().as("size"));

        val arraySizeProjection = mongoTemplate.aggregate(aggregation, Book.class, ArraySizeProjection.class).getUniqueMappedResult();
        return arraySizeProjection == null ? 0 : arraySizeProjection.getSize();
    }
}
