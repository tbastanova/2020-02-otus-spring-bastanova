package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.IOContext;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.impl.ConsoleContextImpl;
import ru.otus.spring.service.impl.IOServiceImpl;


@PropertySource("classpath:application.properties")
@Configuration
public class ServiceConfig {

    @Bean
    IOContext ioContext() {
        return new ConsoleContextImpl();
    }

    @Bean
    IOService ioService() {
        return new IOServiceImpl(ioContext());
    }
}
