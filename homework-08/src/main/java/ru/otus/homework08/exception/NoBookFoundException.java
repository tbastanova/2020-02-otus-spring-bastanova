package ru.otus.homework08.exception;

public class NoBookFoundException extends RuntimeException {

    public NoBookFoundException(Throwable e) {
        super(e);
    }
}
