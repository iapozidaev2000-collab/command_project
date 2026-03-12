package ru.commandproject.output;

import ru.commandproject.collection.BookCollection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class AppendFileWriter {

    private final File file;
    public AppendFileWriter(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Путь к файлу не может быть пустым");
        }

        this.file = new File(filePath);

        // Получаем родительскую папку (например, из "logs/app.log" получим "logs")
        File parent = file.getParentFile();

        // Если путь содержит папки и они еще не созданы — создаем их
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new RuntimeException("Не удалось создать необходимые директории по пути: " + parent.getAbsolutePath());
            }
        }

        // Дополнительная проверка: не является ли указанный путь уже существующей папкой
        if (file.exists() && file.isDirectory()) {
            throw new IllegalArgumentException("Указанный путь является директорией, а не файлом: " + filePath);
        }
    }

    public AppendFileWriter(Path filePath) {
        this.file = filePath.toFile();
    }

    public void appendCollection(BookCollection<?> collection) {
        appendCollection(null, collection);
    }

    public void appendCollection(String title, BookCollection<?> collection) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (title != null) {
                writer.write("=== " + title + " ===");
                writer.newLine();
            }

            // Проходим по всем элементам коллекции
            for (Object item : collection) {
                writer.write(item.toString());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Ошибка при записи коллекции: " + e.getMessage());
        }
    }

    public void appendValue(Object value) {
        appendValue(null, value);
    }

    public void appendValue(String title, Object value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (title != null) {
                writer.write(title + ": ");
            }
            writer.write(value.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Ошибка при записи значения: " + e.getMessage());
        }
    }
}
