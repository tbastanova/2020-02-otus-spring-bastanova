package ru.otus.spring.service;

import ru.otus.spring.domain.Student;

public interface ConsoleService {
    String getLine(String question);

    void showResult(Student student, int correctAnswersCount);
}
