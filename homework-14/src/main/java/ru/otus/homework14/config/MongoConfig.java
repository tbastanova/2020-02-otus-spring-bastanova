package ru.otus.homework14.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import ru.otus.homework14.repository.mongo.AuthorRepositoryMongo;
import ru.otus.homework14.repository.mongo.BookRepositoryMongo;
import ru.otus.homework14.repository.mongo.CategoryRepositoryMongo;
import ru.otus.homework14.repository.mongo.CommentRepositoryMongo;


@Configuration
public class MongoConfig {
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator(BookRepositoryMongo bookRepository,
                                                                          AuthorRepositoryMongo authorRepository,
                                                                          CategoryRepositoryMongo categoryRepository,
                                                                          CommentRepositoryMongo commentRepository) {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
        commentRepository.deleteAll();
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        return factory;
    }
}
