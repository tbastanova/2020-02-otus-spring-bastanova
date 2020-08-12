package ru.otus.feedmysail.exception;

public class NoProductFoundException extends RuntimeException {

    public NoProductFoundException(Throwable e) {
        super(e);
    }
}
