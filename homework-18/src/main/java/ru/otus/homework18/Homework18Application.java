package ru.otus.homework18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class Homework18Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework18Application.class, args);
    }

}
