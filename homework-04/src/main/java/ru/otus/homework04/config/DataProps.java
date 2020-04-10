package ru.otus.homework04.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("data")
@Data
public class DataProps {
    private int questionsCount;
}
