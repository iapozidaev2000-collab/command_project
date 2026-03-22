package ru.commandproject.output;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.validation.InputValidator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public final class AppendFileWriter {
    private static final String DEFAULT_COLLECTION_TITLE = "Коллекция книг";
    private static final String DEFAULT_VALUE_TITLE = "Найденное значение";

    private final Path filePath;

    public AppendFileWriter(String filePath) {
        this(Path.of(InputValidator.requireNonBlank(filePath, "Путь к файлу")));
    }

    public AppendFileWriter(Path filePath) {
        this.filePath = Objects.requireNonNull(filePath, "Путь к файлу не должен быть null").normalize();
    }

    public void appendCollection(BookCollection<?> collection) {
        appendCollection(null, collection);
    }

    public void appendCollection(String title, BookCollection<?> collection) {
        Objects.requireNonNull(collection, "Коллекция не должна быть null");
        try (BufferedWriter writer = openWriter()) {
            writeHeader(writer, normalizeTitle(title, DEFAULT_COLLECTION_TITLE));

            if (collection.isEmpty()) {
                writeLine(writer, "Коллекция пуста.");
                writeEmptyLine(writer);
                return;
            }

            for (Object item : collection) {
                writeLine(writer, formatCollectionItem(item));
            }
            writeEmptyLine(writer);
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось записать данные в файл: " + filePath, ex);
        }
    }

    public void appendValue(Object value) {
        appendValue(null, value);
    }

    public void appendValue(String title, Object value) {
        Objects.requireNonNull(value, "Значение не должно быть null");
        try (BufferedWriter writer = openWriter()) {
            writeHeader(writer, normalizeTitle(title, DEFAULT_VALUE_TITLE));
            writeLine(writer, String.valueOf(value));
            writeEmptyLine(writer);
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось записать данные в файл: " + filePath, ex);
        }
    }

    private String normalizeTitle(String title, String fallbackTitle) {
        return title == null || title.isBlank() ? fallbackTitle : title.trim();
    }

    private void writeHeader(BufferedWriter writer, String title) throws IOException {
        writeLine(writer, "=== " + title + " ===");
    }

    private String formatCollectionItem(Object item) {
        if (item instanceof Book book) {
            return book.getPages() + ";" + book.getTitle() + ";" + book.getReleaseDate();
        }
        return String.valueOf(item);
    }

    private void writeLine(BufferedWriter writer, String value) throws IOException {
        writer.write(value);
        writer.newLine();
    }

    private void writeEmptyLine(BufferedWriter writer) throws IOException {
        writeLine(writer, "");
    }

    private BufferedWriter openWriter() throws IOException {
        validateTargetPath();

        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        return Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    private void validateTargetPath() {
        if (Files.exists(filePath) && Files.isDirectory(filePath)) {
            throw new IllegalArgumentException("Путь указывает на директорию, а не на файл: " + filePath);
        }
    }
}
