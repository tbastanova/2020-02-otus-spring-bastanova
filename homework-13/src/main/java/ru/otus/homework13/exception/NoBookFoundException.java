package ru.otus.homework13.exception;

public class NoBookFoundException extends RuntimeException {

    public NoBookFoundException(Throwable e) {
        super(e);
    }
}
