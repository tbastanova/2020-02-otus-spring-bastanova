package ru.otus.homework14.config;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework14.model.mongo.AuthorMongo;
import ru.otus.homework14.model.mongo.BookMongo;
import ru.otus.homework14.model.mongo.CategoryMongo;
import ru.otus.homework14.model.mongo.CommentMongo;
import ru.otus.homework14.repository.mongo.AuthorRepositoryMongo;
import ru.otus.homework14.repository.mongo.BookRepositoryMongo;
import ru.otus.homework14.repository.mongo.CategoryRepositoryMongo;
import ru.otus.homework14.repository.mongo.CommentRepositoryMongo;

@Configuration
public class WriterConfig {
    @Bean
    public RepositoryItemWriter<AuthorMongo> authorWriter(AuthorRepositoryMongo authorRepository) {
        return new RepositoryItemWriterBuilder<AuthorMongo>()
                .repository(authorRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public RepositoryItemWriter<CategoryMongo> categoryWriter(CategoryRepositoryMongo categoryRepository) {
        return new RepositoryItemWriterBuilder<CategoryMongo>()
                .repository(categoryRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public RepositoryItemWriter<BookMongo> bookWriter(BookRepositoryMongo bookRepository) {
        return new RepositoryItemWriterBuilder<BookMongo>()
                .repository(bookRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public RepositoryItemWriter<CommentMongo> commentWriter(CommentRepositoryMongo commentRepository) {
        return new RepositoryItemWriterBuilder<CommentMongo>()
                .repository(commentRepository)
                .methodName("insert")
                .build();
    }
}
