package ru.otus.homework07.exception;

public class NoBookFoundException extends RuntimeException {

    public NoBookFoundException(Throwable e) {
        super(e);
    }
}
