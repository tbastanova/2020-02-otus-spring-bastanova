package ru.otus.spring.service;

public class ExamException extends RuntimeException {
    public ExamException(String message) {
        super(message);
    }

    public ExamException(String message, Throwable cause) {
        super(message, cause);
    }
}
