package ru.otus.homework10.exception;

public class NoCategoryFoundException extends RuntimeException {

    public NoCategoryFoundException(Throwable e) {
        super(e);
    }
}
