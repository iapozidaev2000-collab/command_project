package ru.commandproject.app;

import ru.commandproject.util.ConsoleIO;

import java.nio.charset.Charset;
import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, Charset.defaultCharset())) {
            ApplicationLoop applicationLoop = new ApplicationLoop(scanner);
            applicationLoop.run();
        }
        ConsoleIO.out().flush();
    }
}
