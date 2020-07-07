package ru.otus.homework11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Homework11Application {
    public static void main(String[] args) {
        SpringApplication.run(Homework11Application.class, args);
    }
}
