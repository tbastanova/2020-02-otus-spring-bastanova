package ru.otus.feedmysail.exception;

public class NoCategoryFoundException extends RuntimeException {

    public NoCategoryFoundException(Throwable e) {
        super(e);
    }
}
