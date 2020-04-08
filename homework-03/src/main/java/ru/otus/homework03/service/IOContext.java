package ru.otus.homework03.service;

import java.io.InputStream;
import java.io.PrintStream;

public interface IOContext {

    InputStream getIn();

    PrintStream getOut();
}