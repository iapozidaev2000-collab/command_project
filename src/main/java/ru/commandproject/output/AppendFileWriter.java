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
        validateAndPreparePath();
    }

    public AppendFileWriter(Path filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("Path не может быть null");
        }
        this.file = filePath.toFile();
        validateAndPreparePath();
    }

    private void validateAndPreparePath() {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new RuntimeException("Не удалось создать директории: " + parent.getAbsolutePath());
            }
        }
        if (file.exists() && file.isDirectory()) {
            throw new IllegalArgumentException("Путь является директорией: " + file.getAbsolutePath());
        }
    }
    public void appendCollection(BookCollection<?> collection) {
        appendCollection(null, collection);
    }

    public void appendCollection(String title, BookCollection<?> collection) {
        if (collection == null) {
            System.err.println("Попытка записи null коллекции");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (title != null) {
                writer.write("=== " + title + " ===");
                writer.newLine();
            }

            for (Object item : collection) {
                if (item != null) {
                    writer.write(item.toString());
                    writer.newLine();
                }
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

