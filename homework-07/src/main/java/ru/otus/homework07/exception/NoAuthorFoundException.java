package ru.otus.homework07.exception;

public class NoAuthorFoundException extends RuntimeException {

    public NoAuthorFoundException(Throwable e) {
        super(e);
    }
}
