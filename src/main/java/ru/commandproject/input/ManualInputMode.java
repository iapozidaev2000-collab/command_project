package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;

import ru.commandproject.validation.InputValidator;
import ru.commandproject.util.DateParser;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

public final class ManualInputMode implements InputMode<Object> {
    private final Scanner scanner;
    private final PrintStream out;

    public ManualInputMode(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public ManualInputMode() {
        this(new Scanner(System.in), System.out);
    }

    @Override
    public BookCollection<Object> read(int count) {
        // 1. Сначала проверка
        if (count <= 0) {
            return new BookCollection<>();
        }

        // 2. Создание стандартной коллекции
        BookCollection<Object> books = new BookCollection<>();

        out.println("(Введите 'exit' в любой момент для отмены)");

        try {
            for (int i = 0; i < count; i++) {
                out.println("\nВвод книги [" + (i + 1) + "/" + count + "]:");

                int pages = readPages();
                String title = readTitle();
                LocalDate releaseDate = readReleaseDate();

                books.add(Book.builder()
                        .pages(pages)
                        .title(title)
                        .releaseDate(releaseDate)
                        .build());
            }
        } catch (InterruptedException e) {
            out.println("\nВвод прерван пользователем.");
        } catch (IllegalStateException e) {
            out.println("\nКритическая ошибка: " + e.getMessage());
        }

        return books;
    }

    private String readLineOrThrow() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Ввод завершен до окончания чтения данных.");
        }
        return scanner.nextLine();
    }

    private String getValidatedInput(String prompt) throws InterruptedException {
        out.print(prompt);
        String input = readLineOrThrow().trim();
        if ("exit".equalsIgnoreCase(input)) {
            throw new InterruptedException();
        }
        return input;
    }

    private int readPages() throws InterruptedException {
        while (true) {
            try {
                String input = getValidatedInput("Введите количество страниц: ");
                // Используем проектный валидатор
                return InputValidator.parsePositiveInt(input, "Количество страниц");
            } catch (Exception e) {
                out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private String readTitle() throws InterruptedException {
        while (true) {
            try {
                String input = getValidatedInput("Введите название книги: ");
                // Используем проектный валидатор
                return InputValidator.requireNonBlank(input, "Название не может быть пустым");
            } catch (Exception e) {
                out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private LocalDate readReleaseDate() throws InterruptedException {
        while (true) {
            try {
                String input = getValidatedInput("Введите дату выхода (ГГГГ-ММ-ДД): ");
                // Используем проектный парсер
                return DateParser.parse(input);
            } catch (Exception e) {
                out.println("Ошибка: Неверный формат даты.");
            }
        }
    }
}
