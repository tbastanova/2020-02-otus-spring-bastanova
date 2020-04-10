package ru.otus.homework04.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework04.service.IOContext;
import ru.otus.homework04.service.IOService;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {
    private final Scanner scanner;
    private final PrintStream out;
    private final IOContext ctx;

    public IOServiceImpl() {
        ctx = new ConsoleContextImpl();
        this.scanner = new Scanner(ctx.getIn());
        this.out = ctx.getOut();
    }

    public String getLine(String questionText) {
        printLine(questionText);
        return scanner.nextLine();
    }

    public void printLine(String message) {
        out.println(message);
    }

    public void printFormatLine(String format, Object... args) {
        out.printf(format, args);
        printLine("");
    }


}
