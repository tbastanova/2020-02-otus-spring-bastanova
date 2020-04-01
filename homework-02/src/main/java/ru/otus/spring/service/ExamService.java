package ru.otus.spring.service;

import ru.otus.spring.domain.Student;

public interface ExamService {
    void startExam();

    void showResult(Student student, int correctAnswersCount);
}
