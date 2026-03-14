package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;//обработка ошибок при работе с данными
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public final class ManualInputMode implements InputMode<Object> {
    private final Scanner scanner;//читаем ввод с консоли

    public ManualInputMode() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public BookCollection<Object> read(int count) {
        BookCollection<Object> collection = createCollection();
        if (count <= 0) {
            System.out.println("Предупреждение: Количество элементов должно быть больше 0.");
            return collection;
        }

        System.out.println("Режим ручного ввода. Нужно ввести книг: " + count);

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
        }
        return collection;
    }

    private int readPages() {
        while (true) {
            System.out.print("Введите количество страниц: ");
            String input = scanner.nextLine();
            try {
                int pages = Integer.parseInt(input);
                if (pages > 0) return pages;
                System.out.println("Ошибка: Число страниц должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число.");
            }
        }
    }

    private String readTitle() {
        while (true) {
            System.out.print("Введите название книги: ");
            String title = scanner.nextLine().trim();
            if (!title.isEmpty()) return title;
            System.out.println("Ошибка: Название не может быть пустым.");
        }
    }

    private LocalDate readReleaseDate() {
        while (true) {
            System.out.print("Введите дату выхода (ГГГГ-ММ-ДД): ");
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: Неверный формат даты. Используйте ГГГГ-ММ-ДД.");
            }
        }
    }

    private BookCollection<Object> createCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object e) { storage.add(e); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }

    public void startCollectionInput(BookCollection<Object> testCollection) {//
        System.out.print("Сколько книг вы хотите добавить в коллекцию? ");
        int count = 0;

        // Валидация ввода count
        while (count <= 0) {
            try {
                count = Integer.parseInt(scanner.nextLine());
                if (count <= 0) System.out.println("Введите число больше 0:");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число:");
            }
        }

        // Вызов метода read для получения данных
        BookCollection<Object> result = read(count);

        // Перенос данных в коллекцию
        for (Object book : result) {
            testCollection.add(book);
        }

        System.out.println("\nВвод завершен. Коллекция заполнена.");
    }
}
