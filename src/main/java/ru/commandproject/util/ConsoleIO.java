package ru.commandproject.util;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class ConsoleIO {
    private static final PrintStream UTF8_OUT = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    private ConsoleIO() {
    }

    public static PrintStream out() {
        return UTF8_OUT;
    }
}
