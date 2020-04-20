package ru.otus.homework05.exception;

public class NoAuthorFoundException extends RuntimeException {

    public NoAuthorFoundException(Throwable e) {
        super(e);
    }
}
