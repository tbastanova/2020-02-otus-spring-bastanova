package ru.otus.feedmysail.exception;

public class NoUserFoundException extends RuntimeException {

    public NoUserFoundException(Throwable e) {
        super(e);
    }
}
