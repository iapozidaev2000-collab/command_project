package ru.commandproject.manual;

import ru.commandproject.app.ApplicationLoop;
import ru.commandproject.app.command.AppCommand;
import ru.commandproject.util.ConsoleIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class AppLoopSmokeManualTest {
    private AppLoopSmokeManualTest() {
    }

    public static void main(String[] args) {
        String input = AppCommand.EXIT.code() + "\n";
        ByteArrayInputStream inBuffer = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();

        try (Scanner scanner = new Scanner(inBuffer, StandardCharsets.UTF_8);
             PrintStream out = new PrintStream(outBuffer, true, StandardCharsets.UTF_8)) {
            ApplicationLoop loop = new ApplicationLoop(scanner, out);
            loop.run();
        }

        String output = outBuffer.toString(StandardCharsets.UTF_8);
        assertContains(output, "==== Консоль книг ====");
        assertContains(output, AppCommand.EXIT.menuLine());
        assertContains(output, "Выход из программы.");

        ConsoleIO.out().println("Тест цикла приложения: УСПЕШНО");
    }

    private static void assertContains(String output, String expectedPart) {
        if (!output.contains(expectedPart)) {
            throw new IllegalStateException("Ожидался фрагмент: " + expectedPart + "\nФактический вывод:\n" + output);
        }
    }
}
