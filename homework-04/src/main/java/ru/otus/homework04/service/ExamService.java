package ru.otus.homework04.service;

import ru.otus.homework04.domain.Student;

public interface ExamService {
    void startExam();

    void showResult(Student student, int correctAnswersCount);
}
