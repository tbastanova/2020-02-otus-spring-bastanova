package ru.otus.homework09.exception;

public class NoCategoryFoundException extends RuntimeException {

    public NoCategoryFoundException(Throwable e) {
        super(e);
    }
}
