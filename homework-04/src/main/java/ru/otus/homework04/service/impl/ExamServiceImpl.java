package ru.otus.homework04.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework04.domain.Student;
import ru.otus.homework04.service.*;

@Service
public class ExamServiceImpl implements ExamService {
    private final StudentService studentService;
    private final QuestionsService questionsService;
    private final IOService ioService;
    private final int totalQuestionsCount;
    private final LocalizationService localizationService;

    public ExamServiceImpl(StudentService studentService, QuestionsService questionsService, LocalizationService localizationService, IOService ioService) {
        this.studentService = studentService;
        this.questionsService = questionsService;
        this.totalQuestionsCount = questionsService.getQuestionsCount();
        this.localizationService = localizationService;
        this.ioService = ioService;
    }

    public void startExam() {
        try {
            Student student = studentService.register();
            int correctAnswersCount = questionsService.ask();
            showResult(student, correctAnswersCount);
        } catch (ExamException e) {
            switch (e.getParentClassName()) {
                case ("java.io.FileNotFoundException"):
                    ioService.printFormatLine(localizationService.getValue("error.fileNotFound"), e.getMessage());
                    break;
                default:
                    ioService.printFormatLine(localizationService.getValue("error.unexpectedError"), e.getMessage());
                    break;
            }
        }

    }

    public void showResult(Student student, int correctAnswersCount) {
        ioService.printLine("_______________________________________________________________________");
        ioService.printFormatLine(localizationService.getValue("result.header"), student.getLastName().toUpperCase(), student.getFirstName().toUpperCase());
        ioService.printFormatLine(localizationService.getValue("result.count"), correctAnswersCount, totalQuestionsCount);
    }
}
