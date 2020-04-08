package ru.otus.homework03.service;

public interface IOService {
    String getLine(String question);

    void printLine(String message);

    void printFormatLine(String format, Object... args);
}
