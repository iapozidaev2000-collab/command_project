package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.util.ConsoleIO;
import ru.commandproject.util.DateParser;
import ru.commandproject.validation.InputValidator;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public final class ManualInputMode implements InputMode<Book> {
    private final Scanner scanner;
    private final PrintStream out;

    public ManualInputMode(Scanner scanner) {
        this(scanner, ConsoleIO.out());
    }

    public ManualInputMode(Scanner scanner, PrintStream out) {
        this.scanner = Objects.requireNonNull(scanner, "Scanner не должен быть null");
        this.out = Objects.requireNonNull(out, "PrintStream не должен быть null");
    }

    @Override
    public BookCollection<Book> read(int count) {
        InputValidator.requirePositive(count, "Количество элементов");

        BookCollection<Book> books = new BookCollection<>();
        for (int i = 0; i < count; i++) {
            out.println();
            out.println("Ввод книги " + (i + 1) + " из " + count + ".");

            int pages = readPages();
            String title = readTitle();
            LocalDate releaseDate = readReleaseDate();

            books.add(Book.builder()
                    .pages(pages)
                    .title(title)
                    .releaseDate(releaseDate)
                    .build());
        }

        return books;
    }

    private int readPages() {
        while (true) {
            out.print("Введите количество страниц: ");
            String raw = readLineOrThrow();
            try {
                return InputValidator.parsePositiveInt(raw, "Количество страниц");
            } catch (IllegalArgumentException ex) {
                out.println(ex.getMessage());
            }
        }
    }

    private String readTitle() {
        while (true) {
            out.print("Введите название: ");
            String raw = readLineOrThrow();
            try {
                return InputValidator.requireNonBlank(raw, "Название");
            } catch (IllegalArgumentException ex) {
                out.println(ex.getMessage());
            }
        }
    }

    private LocalDate readReleaseDate() {
        while (true) {
            out.print("Введите дату выхода (yyyy-MM-dd): ");
            String raw = readLineOrThrow();
            try {
                return DateParser.parse(raw);
            } catch (IllegalArgumentException ex) {
                out.println(ex.getMessage());
            }
        }
    }

    private String readLineOrThrow() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Ввод завершен до окончания чтения данных.");
        }
        return scanner.nextLine();
    }
}
