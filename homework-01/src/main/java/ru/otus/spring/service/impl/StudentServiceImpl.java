package ru.otus.spring.service.impl;

import ru.otus.spring.domain.Student;
import ru.otus.spring.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StudentServiceImpl implements StudentService {
    private Student student;

    public StudentServiceImpl(Student student) {
        this.student = student;
    }

    public Student register() throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите фамилию:");
        String lastName = reader.readLine();
        student.setLastName(lastName);
        System.out.println("Введите имя:");
        String firstName = reader.readLine();
        student.setFirstName(firstName);
        return student;
    }
}
