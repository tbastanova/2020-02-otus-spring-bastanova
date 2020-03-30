package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.ConsoleService;
import ru.otus.spring.service.ExamService;
import ru.otus.spring.service.QuestionsService;
import ru.otus.spring.service.StudentService;

@Service
public class ExamServiceImpl implements ExamService {
    private final StudentService studentService;
    private final QuestionsService questionsService;
    private final QuestionsDao questionsDao;
    private final ConsoleService consoleService;

    public ExamServiceImpl(StudentService studentService, QuestionsService questionsService, QuestionsDao questionsDao, ConsoleService consoleService) {
        this.studentService = studentService;
        this.questionsService = questionsService;
        this.questionsDao = questionsDao;
        this.consoleService = consoleService;
    }

    public void startExam() {
        Student student = studentService.register();
        int correctAnswersCount = questionsService.ask(questionsDao);
        consoleService.showResult(student, correctAnswersCount);
    }
}
