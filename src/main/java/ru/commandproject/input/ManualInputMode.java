package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import java.util.Scanner;

public class ManualInputMode {
    private final Scanner scanner;

    public ManualInputMode() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Читает данные из консоли и добавляет их в коллекцию.
     * Запись в файл здесь больше не производится.
     */
    public void startCollectionInput(BookCollection<Object> collection) {
        if (collection == null) {
            System.err.println("Ошибка: коллекция не инициализирована.");
            return;
        }

        System.out.println("Введите данные книг (для завершения введите 'exit'):");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input == null || "exit".equalsIgnoreCase(input.trim())) {
                break;
            }

            if (input.isBlank()) {
                System.out.println("Пустая строка пропущена.");
                continue;
            }

            // Только добавляем в оперативную память (в коллекцию)
            collection.add(input);
        }

        System.out.println("Ввод завершен. Элементов добавлено: " + getCollectionSize(collection));
    }

    /**
     * Служебный метод для получения размера (если ваш интерфейс это позволяет)
     */
    private int getCollectionSize(BookCollection<?> collection) {
        int count = 0;
        for (Object ignored : collection) count++;
        return count;
    }
}
