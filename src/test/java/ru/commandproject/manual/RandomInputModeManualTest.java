package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public final class RandomInputMode implements InputMode<Object> {

    @Override
    public BookCollection<Object> read(int count) {
        BookCollection<Object> collection = createCollection();
        String[] mockData = {"Java 21", "Spring Boot", "Stream API", "Docker", "JUnit"};
        Random random = new Random();

        Stream.generate(() -> mockData[random.nextInt(mockData.length)])
                .limit(count)
                .forEach(collection::add);

        System.out.println("Случайная генерация завершена. Создано элементов: " + count);
        return collection;
    }

    private BookCollection<Object> createCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object e) { storage.add(e); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }
}
