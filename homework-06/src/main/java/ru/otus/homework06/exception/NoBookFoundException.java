package ru.otus.homework06.exception;

public class NoBookFoundException extends RuntimeException {

    public NoBookFoundException(Throwable e) {
        super(e);
    }
}
