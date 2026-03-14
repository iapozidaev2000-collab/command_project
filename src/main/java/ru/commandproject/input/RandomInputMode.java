package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomInputMode implements InputMode<Object> {
    private final Random random;
    // Заготовки для случайных названий
    private static final String[] TITLES = {"Java для профи", "Алгоритмы", "Чистый код", "Философия Java", "Spring в действии"};

    public RandomInputMode() {
        this.random = new Random();
    }

    @Override
    public BookCollection<Object> read(int count) {
        BookCollection<Object> collection = createCollection();
        if (count <= 0) {
            System.out.println("Предупреждение: Количество элементов должно быть больше 0.");
            return collection;
        }

        System.out.println("Режим случайной генерации. Создается книг: " + count);

        for (int i = 0; i < count; i++) {
            int pages = random.nextInt(1000) + 50; // От 50 до 1050 страниц
            String title = TITLES[random.nextInt(TITLES.length)] + " #" + (random.nextInt(100));
            LocalDate releaseDate = generateRandomDate();

            collection.add(Book.builder()
                    .pages(pages)
                    .title(title)
                    .releaseDate(releaseDate)
                    .build());
        }
        return collection;
    }

    private LocalDate generateRandomDate() {
        long minDay = LocalDate.of(1990, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private BookCollection<Object> createCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object e) { storage.add(e); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }

    public void startCollectionInput(BookCollection<Object> testCollection) {
        int count = 5;
        System.out.println("Генерация 5 случайных книг...");

        BookCollection<Object> result = read(count);

        for (Object book : result) {
            testCollection.add(book);
        }

        System.out.println("Генерация завершена. Коллекция заполнена случайными данными.");
    }
}
