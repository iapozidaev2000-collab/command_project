package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.FileInputMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class FileInputModeManualTest {

    public static void main(String[] args) {
        String inputPath = "test_input.txt";


        prepareTestData(inputPath);


        FileInputMode fileInputMode = new FileInputMode(inputPath);
        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск теста: Файл -> Коллекция ===");


        fileInputMode.startCollectionInput(testCollection);


        System.out.println("\n--- Содержимое коллекции после загрузки ---");
        for (Object book : testCollection) {
            System.out.println(book);
        }

        System.out.println("\nТест завершен.");
        System.out.println("Входной файл: " + Path.of(inputPath).toAbsolutePath());
    }

    private static void prepareTestData(String filePath) {
        try {

            List<String> lines = List.of(
                    "Clean Code;450;2008-08-01",
                    "Design Patterns;395;1994-10-21",
                    "   ", // Пустая строка для проверки пропуска
                    "Effective Java;412;2017-12-27",
                    "Refactoring;448;2018-11-19"
            );
            Files.write(Path.of(filePath), lines);
            System.out.println("Тестовые данные подготовлены: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }
    }

    private static BookCollection<Object> createTestCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object element) { storage.add(element); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }
}
