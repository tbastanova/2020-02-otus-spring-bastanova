package ru.otus.homework08.exception;

public class NoAuthorFoundException extends RuntimeException {

    public NoAuthorFoundException(Throwable e) {
        super(e);
    }
}
