package ru.commandproject.util;

import java.io.PrintStream;

public final class ConsoleIO {
    private static final PrintStream OUT = System.out;

    private ConsoleIO() {
    }

    public static PrintStream out() {
        return OUT;
    }
}
