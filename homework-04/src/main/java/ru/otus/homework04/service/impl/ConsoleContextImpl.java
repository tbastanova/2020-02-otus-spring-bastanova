package ru.otus.homework04.service.impl;

import ru.otus.homework04.service.IOContext;

import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleContextImpl implements IOContext {
    private final InputStream in;
    private final PrintStream out;

    public ConsoleContextImpl() {
        this.in = System.in;
        this.out = new PrintStream(System.out);
    }

    public InputStream getIn() {
        return in;
    }

    public PrintStream getOut() {
        return out;
    }
}
