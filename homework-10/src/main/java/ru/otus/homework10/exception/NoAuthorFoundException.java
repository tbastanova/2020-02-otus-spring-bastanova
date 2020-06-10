package ru.otus.homework10.exception;

public class NoAuthorFoundException extends RuntimeException {

    public NoAuthorFoundException(Throwable e) {
        super(e);
    }
}
