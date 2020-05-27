package ru.otus.homework08.exception;

public class NoCategoryFoundException extends RuntimeException {

    public NoCategoryFoundException(Throwable e) {
        super(e);
    }
}
