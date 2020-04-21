package ru.otus.homework06.exception;

public class NoCategoryFoundException extends RuntimeException {

    public NoCategoryFoundException(Throwable e) {
        super(e);
    }
}
