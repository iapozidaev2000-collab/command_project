package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public final class FileInputMode implements InputMode<Object> {
    private final String filePath;

    public FileInputMode(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public BookCollection<Object> read(int count) {
        BookCollection<Object> collection = createCollection();
        File file = new File(filePath);

        try (Scanner fileScanner = new Scanner(file)) {
            int readCount = 0;
            System.out.println("Чтение данных из файла: " + filePath);

            while (fileScanner.hasNextLine() && readCount < count) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // Разделяем строку по точке с запятой
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    try {
                        String title = parts[0].trim();
                        int pages = Integer.parseInt(parts[1].trim());
                        LocalDate date = LocalDate.parse(parts[2].trim());

                        collection.add(Book.builder()
                                .pages(pages)
                                .title(title)
                                .releaseDate(date)
                                .build());
                        readCount++;
                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.out.println("Ошибка в строке: '" + line + "'. Пропуск.");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден по пути " + filePath);
        }

        return collection;
    }

    private BookCollection<Object> createCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object e) { storage.add(e); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }

    public void startCollectionInput(BookCollection<Object> testCollection) {
        int limit = 10;
        BookCollection<Object> result = read(limit);

        int counter = 0;
        for (Object book : result) {
            testCollection.add(book);
            counter++;
        }

        if (counter > 0) {
            System.out.println("Файл успешно прочитан. Добавлено объектов: " + counter);
        } else {
            System.out.println("Данные не были загружены. Проверьте путь к файлу или его формат.");
        }
    }
}
