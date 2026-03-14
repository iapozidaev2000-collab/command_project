package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.RandomInputMode; // Импортируем созданный класс
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class RandomInputModeManualTest {

    public static void main(String[] args) {
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);

        RandomInputMode inputMode = new RandomInputMode();
        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск теста: RandomInputMode -> Коллекция -> Файл ===");

        inputMode.startCollectionInput(testCollection);

        System.out.println("\n--- Сохранение случайных данных в файл ---");
        writer.appendCollection("Результат случайной генерации", testCollection);

        System.out.println("\nТест завершен.");
        System.out.println("Путь к файлу: " + new File(testFilePath).getAbsolutePath());
        System.out.println("Проверьте, что в файле появились записи с заголовком 'Результат случайной генерации'.");
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

