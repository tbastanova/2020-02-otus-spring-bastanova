package ru.otus.homework14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.homework14.model.mongo.CommentMongo;

@Repository
public interface CommentRepositoryMongo extends MongoRepository<CommentMongo, String> {
}
