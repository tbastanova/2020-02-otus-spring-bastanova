package ru.otus.homework13.exception;

public class NoAuthorFoundException extends RuntimeException {

    public NoAuthorFoundException(Throwable e) {
        super(e);
    }
}
