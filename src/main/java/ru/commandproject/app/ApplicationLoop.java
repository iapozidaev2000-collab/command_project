package ru.commandproject.app;

import ru.commandproject.util.ConsoleIO;

import java.io.PrintStream;
import java.util.Scanner;

public final class ApplicationLoop {
    private final Scanner scanner;
    private final PrintStream out;

    public ApplicationLoop(Scanner scanner) {
        this(scanner, ConsoleIO.out());
    }

    public ApplicationLoop(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            if (!scanner.hasNextLine()) {
                out.println();
                out.println("Ввод завершен. Приложение остановлено.");
                break;
            }
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "0" -> running = false;
                default -> out.println("Неизвестная команда. Повторите ввод.");
            }
        }

        out.println("Выход из программы.");
    }

    private void printMenu() {
        out.println();
        out.println("==== Консоль книг ====");
        out.println("0 - Выход");
        out.print("Выберите пункт: ");
    }
}
