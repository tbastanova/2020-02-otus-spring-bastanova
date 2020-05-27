package ru.otus.homework08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.CategoryRepository;
import ru.otus.homework08.repository.CommentRepository;

@Configuration
public class Config {
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator(BookRepository bookRepository,
                                                                          AuthorRepository authorRepository,
                                                                          CategoryRepository categoryRepository,
                                                                          CommentRepository commentRepository,
                                                                          DataProps dataProps) {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
        commentRepository.deleteAll();
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource(dataProps.getDataPath())});
        return factory;
    }
}
