package ru.otus.homework08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Homework08Application {
    public static void main(String[] args) {
        SpringApplication.run(Homework08Application.class, args);
    }
}