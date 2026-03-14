package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
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
        if (count <= 0) {
            return createEmptyCollection();
        }

        BookCollection<Object> books = createEmptyCollection();

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
                int pages = Integer.parseInt(input);
                if (pages > 0) return pages;
                out.println("Ошибка: Число должно быть больше 0.");
            } catch (NumberFormatException e) {
                out.println("Ошибка: Введите целое число.");
            }
        }
    }

    private String readTitle() throws InterruptedException {
        while (true) {
            String input = getValidatedInput("Введите название книги: ");
            
            if (!input.isBlank()) return input;
            out.println("Ошибка: Название не может быть пустым.");
        }
    }

    private LocalDate readReleaseDate() throws InterruptedException {
        while (true) {
            try {
                String input = getValidatedInput("Введите дату выхода (ГГГГ-ММ-ДД): ");
                
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                out.println("Ошибка: Неверный формат даты (ГГГГ-ММ-ДД).");
            }
        }
    }
    
    private BookCollection<Object> createEmptyCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object e) { storage.add(e); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }
}
