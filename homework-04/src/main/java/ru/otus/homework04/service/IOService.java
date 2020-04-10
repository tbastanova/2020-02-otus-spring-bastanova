package ru.otus.homework04.service;

public interface IOService {
    String getLine(String question);

    void printLine(String message);

    void printFormatLine(String format, Object... args);
}
