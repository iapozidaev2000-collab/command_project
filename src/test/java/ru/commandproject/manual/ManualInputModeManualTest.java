package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class ManualInputModeManualTest {

    public static void main(String[] args) {
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);

        ManualInputMode inputMode = new ManualInputMode();
        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск ручного теста: Чтение -> Коллекция -> Файл ===");

        // Запуск ввода данных (используем метод, который мы заполнили ранее)
        inputMode.startCollectionInput(testCollection);

        System.out.println("\n--- Сохранение данных в файл ---");
        // Оставляем только одну запись в файл, чтобы избежать дубликатов
        writer.appendCollection("Результат ручного ввода", testCollection);

        System.out.println("\nТест завершен.");
        System.out.println("Путь к файлу: " + new File(testFilePath).getAbsolutePath());
        // Исправили текст сообщения, убрав упоминание сортировки
        System.out.println("Проверьте, что в файле появились записи с заголовком 'Результат ручного ввода'.");
    }

    private static BookCollection<Object> createTestCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();

            @Override
            public void add(Object element) {
                storage.add(element);
            }

            @Override
            public Iterator<Object> iterator() {
                return storage.iterator();
            }
        };
    }
}
