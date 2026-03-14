package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public final class ManualInputMode implements InputMode<Object> {
    private final Scanner scanner;

    // Конструктор без параметров, чтобы не ломать остальной код
    public ManualInputMode() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public BookCollection<Object> read(int count) {
        //  Коллекция внутри метода
        BookCollection<Object> collection = new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object e) { storage.add(e); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };

        if (count <= 0) return collection;

        System.out.println("Режим ручного ввода. Нужно ввести книг: " + count);
        System.out.println("(Введите 'exit' для отмены)");

        int addedBefore = 0;
        try {
            for (int i = 0; i < count; i++) {
                System.out.println("\nВвод книги [" + (i + 1) + "/" + count + "]:");

                int pages = readPages();
                String title = readTitle();
                LocalDate releaseDate = readReleaseDate();

                collection.add(Book.builder()
                        .pages(pages)
                        .title(title)
                        .releaseDate(releaseDate)
                        .build());

                addedBefore++;
            }
        } catch (InterruptedException e) {
            System.out.println("\nВвод прерван. Сохранено книг: " + addedBefore);
        }

        return collection;
    }

    // Вспомогательный метод для чтения с проверкой на exit
    private String getValidatedInput(String prompt) throws InterruptedException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if ("exit".equalsIgnoreCase(input)) {
            throw new InterruptedException();
        }
        return input;
    }

    private int readPages() throws InterruptedException {
        while (true) {
            String input = getValidatedInput("Введите количество страниц: ");
            try {
                int pages = Integer.parseInt(input);
                if (pages > 0) return pages;
                System.out.println("Ошибка: Число должно быть > 0.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
        }
    }

    private String readTitle() throws InterruptedException {
        while (true) {
            String title = getValidatedInput("Введите название книги: ");
            if (!title.isEmpty()) return title;
            System.out.println("Ошибка: Название не может быть пустым.");
        }
    }

    private LocalDate readReleaseDate() throws InterruptedException {
        while (true) {
            String input = getValidatedInput("Введите дату выхода (ГГГГ-ММ-ДД): ");
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: Формат ГГГГ-ММ-ДД (например, 2023-01-01).");
            }
        }
    }
}
