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
        this.file = new File(filePath);
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

