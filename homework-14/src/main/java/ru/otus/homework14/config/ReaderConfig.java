package ru.otus.homework14.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework14.model.jpa.AuthorJpa;
import ru.otus.homework14.model.jpa.BookJpa;
import ru.otus.homework14.model.jpa.CategoryJpa;
import ru.otus.homework14.model.jpa.CommentJpa;
import ru.otus.homework14.repository.jpa.AuthorRepositoryJpa;
import ru.otus.homework14.repository.jpa.BookRepositoryJpa;
import ru.otus.homework14.repository.jpa.CategoryRepositoryJpa;
import ru.otus.homework14.repository.jpa.CommentRepositoryJpa;

import java.util.HashMap;

@Configuration
public class ReaderConfig {
    @StepScope
    @Bean
    @Transactional
    public RepositoryItemReader<AuthorJpa> authorReader(AuthorRepositoryJpa authorRepository) {
        return new RepositoryItemReaderBuilder<AuthorJpa>()
                .name("authorItemReader")
                .repository(authorRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CategoryJpa> categoryReader(CategoryRepositoryJpa categoryRepository) {
        return new RepositoryItemReaderBuilder<CategoryJpa>()
                .name("categoryItemReader")
                .repository(categoryRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<BookJpa> bookReader(BookRepositoryJpa bookRepository) {
        return new RepositoryItemReaderBuilder<BookJpa>()
                .name("bookItemReader")
                .repository(bookRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CommentJpa> commentReader(CommentRepositoryJpa commentRepository) {
        return new RepositoryItemReaderBuilder<CommentJpa>()
                .name("commentItemReader")
                .repository(commentRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .build();
    }
}
