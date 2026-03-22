package ru.commandproject.manual;

import ru.commandproject.app.ApplicationLoop;
import ru.commandproject.app.command.AppCommand;
import ru.commandproject.app.command.InputSourceCommand;
import ru.commandproject.app.command.SortAlgorithmCommand;
import ru.commandproject.app.command.SortFieldCommand;
import ru.commandproject.util.ConsoleIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public final class AppFlowManualTest {
    private AppFlowManualTest() {
    }

    public static void main(String[] args) {
        Path outputFile = createTempOutputFile();
        Path foundValueFile = createTempOutputFile();
        String flowInput = String.join(System.lineSeparator(),
                AppCommand.LOAD_COLLECTION.code(), // load
                "2", // count
                InputSourceCommand.MANUAL.code(), // manual input
                "320",
                "Clean Code",
                "2008-08-01",
                "464",
                "Effective Java",
                "2018-01-06",
                AppCommand.SORT_COLLECTION.code(), // sort
                SortAlgorithmCommand.BUBBLE.code(), // bubble
                SortFieldCommand.PAGES.code(), // by pages
                AppCommand.COUNT_PAGES_OCCURRENCES.code(), // count pages occurrences
                "320", // target pages
                "2", // threads
                AppCommand.SAVE_FOUND_VALUE_TO_FILE.code(), // save found value to file
                foundValueFile.toString(),
                "Результат поиска",
                AppCommand.SAVE_COLLECTION_TO_FILE.code(), // save to file
                outputFile.toString(),
                "Тестовая запись",
                AppCommand.PRINT_COLLECTION.code(), // print collection
                AppCommand.EXIT.code()  // exit
        ) + System.lineSeparator();

        ByteArrayInputStream inBuffer = new ByteArrayInputStream(flowInput.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();

        try (Scanner scanner = new Scanner(inBuffer, StandardCharsets.UTF_8);
             PrintStream out = new PrintStream(outBuffer, true, StandardCharsets.UTF_8)) {
            ApplicationLoop loop = new ApplicationLoop(scanner, out);
            loop.run();
        }

        String output = outBuffer.toString(StandardCharsets.UTF_8);
        String fileContent = readOutputFile(outputFile);
        String foundValueFileContent = readOutputFile(foundValueFile);

        assertContains(output, "Коллекция загружена. Размер: 2");
        assertContains(output, "Сортировка выполнена.");
        assertContains(output, "Количество вхождений элемента \"320\": 1");
        assertContains(output, "Найденное значение записано в файл:");
        assertContains(output, "Коллекция записана в файл:");
        assertContains(output, "Текущая коллекция:");
        assertContains(output, "Выход из программы.");

        assertContains(fileContent, "=== Тестовая запись ===");
        assertContains(fileContent, "320;Clean Code;2008-08-01");
        assertContains(fileContent, "464;Effective Java;2018-01-06");
        assertContains(foundValueFileContent, "=== Результат поиска ===");
        assertContains(foundValueFileContent, "pages=320, количество вхождений=1");

        ConsoleIO.out().println("Тест сценария приложения: УСПЕШНО");
    }

    private static Path createTempOutputFile() {
        try {
            return Files.createTempFile("app-flow-", ".txt");
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось создать временный файл для теста", ex);
        }
    }

    private static String readOutputFile(Path outputFile) {
        try {
            return Files.readString(outputFile, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось прочитать файл результата", ex);
        }
    }

    private static void assertContains(String actual, String expectedPart) {
        if (!actual.contains(expectedPart)) {
            throw new IllegalStateException(
                    "Ожидался фрагмент: " + expectedPart + System.lineSeparator()
                            + "Фактический текст:" + System.lineSeparator() + actual
            );
        }
    }
}
