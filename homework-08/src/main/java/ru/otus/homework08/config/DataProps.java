package ru.otus.homework08.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("data.init")
public class DataProps {
    public String dataPath;
}
