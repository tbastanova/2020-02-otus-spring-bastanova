package ru.otus.homework17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Homework17Application {
    public static void main(String[] args) {
        SpringApplication.run(Homework17Application.class, args);
    }
}
