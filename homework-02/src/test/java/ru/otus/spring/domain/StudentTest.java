package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Student")
class StudentTest {
    private static final String FIRST_NAME = "Иван";
    private static final String LAST_NAME = "Иванов";

    @DisplayName("Имя задано корректно")
    @Test
    void getFirstName() {
        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        assertEquals(FIRST_NAME, student.getFirstName());
    }

    @DisplayName("Фамилия задана корректно")
    @Test
    void getLastName() {
        Student student = new Student();
        student.setLastName(LAST_NAME);
        assertEquals(LAST_NAME, student.getLastName());
    }
}