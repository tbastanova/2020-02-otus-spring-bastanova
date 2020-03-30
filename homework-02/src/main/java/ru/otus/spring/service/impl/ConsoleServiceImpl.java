package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.ConsoleService;
import ru.otus.spring.service.LocalizationService;

import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ConsoleService {
    private final Scanner scanner;
    private final int totalAnswersCount;
    private final LocalizationService localizationService;

    public ConsoleServiceImpl(Scanner scanner, Integer totalAnswersCount, LocalizationService localizationService) {
        this.scanner = scanner;
        this.totalAnswersCount = totalAnswersCount;
        this.localizationService = localizationService;
    }

    public String getLine(String questionText) {
        System.out.println(questionText);
        return scanner.nextLine();
    }

    public void showResult(Student student, int correctAnswersCount) {
        System.out.println("_______________________________________________________________________");
        System.out.printf(localizationService.getValue("result.header"), student.getLastName().toUpperCase(), student.getFirstName().toUpperCase());
        System.out.println();
        System.out.printf(localizationService.getValue("result.count"), correctAnswersCount, totalAnswersCount);
        System.out.println();
    }
}
