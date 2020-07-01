package ru.otus.homework16.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.homework16.repository.BookRepositoryJpa;

import java.util.Random;

@Component
public class BooksCountGreaterThenFiveHealthIndicator implements HealthIndicator {

    private final BookRepositoryJpa bookRepositoryJpa;

    private static final long BOOKS_LIMIT = 5;

    public BooksCountGreaterThenFiveHealthIndicator(BookRepositoryJpa bookRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Override
    public Health health() {
        if(bookRepositoryJpa.count()>BOOKS_LIMIT){
            return Health.down().withDetail("message","Внимание! Превышен лимит книг!").build();
        } else {
            return Health.up().withDetail("message", "Лимит книг не превышен. Все в порядке.").build();
        }
    }
}
