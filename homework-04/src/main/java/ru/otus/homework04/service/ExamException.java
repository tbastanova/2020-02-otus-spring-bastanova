package ru.otus.homework04.service;

public class ExamException extends RuntimeException {
    private Throwable e;

    public ExamException(Throwable e) {
        super(e);
        this.e = e;
    }

    public String getParentClassName() {
        return e.getClass().getName();
    }

}
