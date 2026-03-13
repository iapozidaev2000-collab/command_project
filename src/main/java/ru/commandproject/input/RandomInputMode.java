package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public final class RandomInputMode {

    private final String testFilePath;
    private final AppendFileWriter writer;

    public RandomInputMode(String testFilePath) {
        this.testFilePath = testFilePath;
        this.writer = new AppendFileWriter(testFilePath);
    }

    public void execute(int count) {
        File file = new File(testFilePath);
        BookCollection<Object> testCollection = createTestCollection();

        long initialSize = file.exists() ? file.length() : 0;
        System.out.println("Начальный размер файла: " + initialSize + " байт");

        System.out.println("=== Генерация случайных данных ===");
        fillCollectionWithRandomData(testCollection, count);

        if (!testCollection.iterator().hasNext()) {
            System.out.println("Ошибка: Данные не сгенерированы. Запись отменена.");
            return;
        }

        System.out.println("\n--- Сохранение данных в файл ---");
        writer.appendCollection("Результат Random ввода", testCollection);

        if (file.exists()) {
            long finalSize = file.length();
            System.out.println("Путь к файлу: " + file.getAbsolutePath());
            System.out.println("Финальный размер файла: " + finalSize + " байт");
            System.out.println("Добавлено данных: " + (finalSize - initialSize) + " байт");
        }
    }

    private void fillCollectionWithRandomData(BookCollection<Object> collection, int count) {
        String[] mockData = {"Java 21", "Spring Boot", "Stream API", "Docker", "JUnit"};
        Random random = new Random();

        Stream.generate(() -> mockData[random.nextInt(mockData.length)])
                .limit(count)
                .filter(s -> s != null && !s.isEmpty())
                .forEach(collection::add);

        System.out.println("Сгенерировано и добавлено элементов: " + count);
    }

    private BookCollection<Object> createTestCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();

            @Override
            public void add(Object element) {
                if (element != null && !element.toString().isEmpty()) {
                    storage.add(element);
                }
            }

            @Override
            public Iterator<Object> iterator() {
                return storage.iterator();
            }
        };
    }
}