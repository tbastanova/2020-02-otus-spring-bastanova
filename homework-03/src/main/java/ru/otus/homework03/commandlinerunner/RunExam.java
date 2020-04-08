package ru.otus.homework03.commandlinerunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.homework03.service.ExamService;


@Component
public class RunExam implements CommandLineRunner {
    private final ExamService examService;

    public RunExam(ExamService examService) {
        this.examService = examService;
    }

    @Override
    public void run(String... args){
        examService.startExam();
    }
}
