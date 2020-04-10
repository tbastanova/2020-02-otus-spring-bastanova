package ru.otus.homework04.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework04.domain.Student;
import ru.otus.homework04.service.ExamService;
import ru.otus.homework04.service.QuestionsService;
import ru.otus.homework04.service.StudentService;

@ShellComponent
public class ApplicationShellCommands {
    private Student student;
    private Boolean isLogin = false;
    private final StudentService studentService;
    private final QuestionsService questionsService;
    private final ExamService examService;

    public ApplicationShellCommands(StudentService studentService, QuestionsService questionsService, ExamService examService) {
        this.studentService = studentService;
        this.questionsService = questionsService;
        this.examService = examService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login() {
        this.student = studentService.register();
        this.isLogin = true;
        return String.format("Добро пожаловать: %s %s", student.getFirstName(), student.getLastName());
    }

    @ShellMethod(value = "Exam command", key = {"e", "exam"})
    @ShellMethodAvailability(value = "isExamCommandAvailable")
    public String exam() {
        int correctAnswersCount = questionsService.ask();
        examService.showResult(student, correctAnswersCount);
        return "Опрос завершен";
    }

    private Availability isExamCommandAvailable() {
        return this.isLogin ? Availability.available() : Availability.unavailable("Сначала залогиньтесь");
    }
}
