package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public final class FileInputMode implements InputMode<Object> {
    private final String inputPath;

    public FileInputMode(String inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public BookCollection<Object> read(int count) {
        BookCollection<Object> collection = createCollection();
        Path path = Path.of(inputPath);

        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(line -> !line.isBlank())
                    .limit(count) // Используем count как лимит строк
                    .forEach(collection::add);
            System.out.println("Чтение из файла завершено.");
        } catch (IOException e) {
            System.err.println("Ошибка чтения: " + e.getMessage());
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

    public void execute(String inputPath) {
    }
}
