package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public final class FileInputMode {

    private final String outputFilePath;
    private final AppendFileWriter writer;

    public FileInputMode(String outputFilePath) {
        this.outputFilePath = outputFilePath;
        this.writer = new AppendFileWriter(outputFilePath);
    }

    public void execute(String inputPath) {
        Path path = Path.of(inputPath);
        File inputFile = path.toFile();

        if (!inputFile.exists()) {
            System.err.println("Ошибка: Входной файл '" + inputPath + "' не найден.");
            return;
        }

        BookCollection<Object> testCollection = createTestCollection();
        System.out.println("=== Чтение данных из файла: " + inputPath + " ===");

        // Использование Stream API для эффективного чтения и фильтрации
        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(line -> line != null && !line.trim().isEmpty()) 
                    .forEach(testCollection::add);

            System.out.println("Файл успешно обработан.");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        if (!testCollection.iterator().hasNext()) {
            System.out.println("Предупреждение: Данные не найдены. Запись отменена.");
            return;
        }

        System.out.println("\n--- Сохранение данных из файла в результат ---");
        writer.appendCollection("Результат файлового ввода", testCollection);

        System.out.println("Путь к файлу результата: " + new File(outputFilePath).getAbsolutePath());
    }

    private BookCollection<Object> createTestCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();

            @Override
            public void add(Object element) {
                // Дополнительная проверка внутри коллекции
                storage.add(element);
            }

            @Override
            public Iterator<Object> iterator() {
                return storage.iterator();
            }
        };
    }
}