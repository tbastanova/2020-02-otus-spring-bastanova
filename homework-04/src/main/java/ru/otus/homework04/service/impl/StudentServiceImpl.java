package ru.otus.homework04.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework04.domain.Student;
import ru.otus.homework04.service.IOService;
import ru.otus.homework04.service.LocalizationService;
import ru.otus.homework04.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    private final IOService ioService;
    private final LocalizationService localizationService;

    public StudentServiceImpl(IOService ioService, LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    public Student register() {
        Student student = new Student();
        student.setLastName(ioService.getLine(localizationService.getValue("student.lastname")));
        student.setFirstName(ioService.getLine(localizationService.getValue("student.firstname")));
        return student;
    }
}
