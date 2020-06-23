package ru.otus.homework14.config;

import org.modelmapper.ModelMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework14.model.mongo.AuthorMongo;
import ru.otus.homework14.model.mongo.BookMongo;
import ru.otus.homework14.model.mongo.CategoryMongo;
import ru.otus.homework14.model.mongo.CommentMongo;

@Configuration
public class ProcessorConfig {
    @Bean
    @StepScope
    public ItemProcessor authorProcessor(ModelMapper modelMapper) {
        return item -> modelMapper.map(item, AuthorMongo.class);
    }

    @Bean
    @StepScope
    public ItemProcessor categoryProcessor(ModelMapper modelMapper) {
        return item -> modelMapper.map(item, CategoryMongo.class);
    }

    @Bean
    @StepScope
    public ItemProcessor bookProcessor(ModelMapper modelMapper) {
        return item -> modelMapper.map(item, BookMongo.class);
    }

    @Bean
    @StepScope
    public ItemProcessor commentProcessor(ModelMapper modelMapper) {
        return item -> modelMapper.map(item, CommentMongo.class);
    }
}
