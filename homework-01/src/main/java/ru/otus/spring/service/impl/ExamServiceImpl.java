package ru.otus.spring.service.impl;

import ru.otus.spring.domain.Student;
import ru.otus.spring.service.ExamService;
import ru.otus.spring.service.QuestionsService;
import ru.otus.spring.service.StudentService;

import java.io.IOException;

public class ExamServiceImpl implements ExamService {
    private final StudentService studentService;
    private final QuestionsService questionsService;

    public ExamServiceImpl(StudentService studentService, QuestionsService questionsService) {
        this.studentService = studentService;
        this.questionsService = questionsService;
    }

    public void startExam() throws IOException {
        Student student = studentService.register();
        int correctAnswersCount = questionsService.ask();

        System.out.println("_______________________________________________________________________");
        System.out.println("Количество правильных ответов студента " + student.getLastName().toUpperCase() + " " + student.getFirstName().toUpperCase() + ":");
        System.out.println(correctAnswersCount);
    }
}
