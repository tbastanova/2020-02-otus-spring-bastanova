package ru.otus.spring.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.ConsoleService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    private ConsoleService consoleService;
    private final LocalizationService localizationService;

    public StudentServiceImpl(ConsoleService consoleService, MessageSource messageSource, LocalizationService localizationService) {
        this.consoleService = consoleService;
        this.localizationService = localizationService;
    }

    public Student register() {
        Student student = new Student();
        student.setLastName(consoleService.getLine(localizationService.getValue("student.lastname")));
        student.setFirstName(consoleService.getLine(localizationService.getValue("student.firstname")));
        return student;
    }
}
