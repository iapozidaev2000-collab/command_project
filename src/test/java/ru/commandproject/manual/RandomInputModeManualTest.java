package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public final class RandomInputModeManualTest {

    public static void main(String[] args) {
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);

        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск автоматического теста: Случайная генерация -> Коллекция -> Файл ===");

        // Генерация случайных данных
        fillCollectionWithRandomData(testCollection, 5);
        System.out.println("Коллекция заполнена случайными данными (5 элементов).");

        System.out.println("\n--- Сохранение случайных данных в файл ---");
        writer.appendCollection("Результат случайной генерации", testCollection);

        System.out.println("\n--- Тест: Запись с заголовком по умолчанию ---");
        writer.appendCollection(testCollection);

        System.out.println("\nТест завершен.");
        System.out.println("Путь к файлу: " + new File(testFilePath).getAbsolutePath());
        System.out.println("Проверьте файл 'append.txt'.");
    }

    private static void fillCollectionWithRandomData(BookCollection<Object> collection, int count) {
        Random random = new Random();
        String[] sampleData = {"Java", "Kotlin", "Spring", "Maven", "Gradle", "Docker"};

        for (int i = 0; i < count; i++) {
            String randomElement = sampleData[random.nextInt(sampleData.length)] + " #" + (i + 1);
            collection.add(randomElement);
        }
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

