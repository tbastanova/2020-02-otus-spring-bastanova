package ru.otus.homework05.exception;

public class NoBookFoundException extends RuntimeException {

    public NoBookFoundException(Throwable e) {
        super(e);
    }
}
