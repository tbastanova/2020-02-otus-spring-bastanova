package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    private ru.otus.spring.service.IOService ioService;
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
