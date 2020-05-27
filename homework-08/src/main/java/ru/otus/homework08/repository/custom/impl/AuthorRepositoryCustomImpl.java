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
import ru.otus.homework08.repository.custom.AuthorRepositoryCustom;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Data
    private class ArraySizeProjection {
        private int size;
    }

    @Override
    public void removeBooksArrayElementsById(String id) {
        val query = Query.query(Criteria.where("$id").is(id));
        val update = new Update().pull("authors", query);
        mongoTemplate.updateMulti(new Query(), update, "book");
    }

    @Override
    public long getBooksArrayLengthByAuthorId(String authorId) {
        val aggregation = Aggregation.newAggregation(
                match(where("id").is(authorId)),
                project().andExclude("_id").and("books").size().as("size"));

        val arraySizeProjection = mongoTemplate.aggregate(aggregation, Author.class, ArraySizeProjection.class).getUniqueMappedResult();
        return arraySizeProjection == null ? 0 : arraySizeProjection.getSize();
    }
}
