package ru.otus.homework03.service;

import ru.otus.homework03.domain.Student;

public interface ExamService {
    void startExam();

    void showResult(Student student, int correctAnswersCount);
}
