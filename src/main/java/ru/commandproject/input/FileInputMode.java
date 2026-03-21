package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.util.DateParser;
import ru.commandproject.validation.InputValidator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public final class FileInputMode implements InputMode<Book> {
    private static final String FIELD_DELIMITER = ";";

    private final Path filePath;

    public FileInputMode(String filePath) {
        this(Path.of(InputValidator.requireNonBlank(filePath, "Путь к файлу")));
    }

    public FileInputMode(Path filePath) {
        this.filePath = Objects.requireNonNull(filePath, "Путь к файлу не должен быть null");
    }

    @Override
    public BookCollection<Book> read(int count) {
        InputValidator.requirePositive(count, "Количество элементов");
        validateFilePath();

        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            BookCollection<Book> books = BookCollection.fromStream(
                    toNumberedLines(lines)
                            .filter(line -> !line.text().isBlank())
                            .limit(count)
                            .map(this::parseBook)
            );

            if (books.size() < count) {
                throw new IllegalStateException(
                        "В файле недостаточно записей. Запрошено: " + count + ", найдено: " + books.size()
                );
            }

            return books;
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось прочитать файл: " + filePath, ex);
        }
    }

    private void validateFilePath() {
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("Файл не найден: " + filePath);
        }
        if (Files.isDirectory(filePath)) {
            throw new IllegalArgumentException("Указанный путь является директорией: " + filePath);
        }
    }

    private Stream<SourceLine> toNumberedLines(Stream<String> lines) {
        int[] lineNumber = {0};
        return lines.map(line -> new SourceLine(++lineNumber[0], line.trim()));
    }

    private Book parseBook(SourceLine line) {
        String[] parts = line.text().split(FIELD_DELIMITER, -1);
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Строка " + line.number() + " должна содержать 3 поля в формате pages;title;releaseDate"
            );
        }

        try {
            return Book.builder()
                    .pages(InputValidator.parsePositiveInt(parts[0], "Количество страниц"))
                    .title(InputValidator.requireNonBlank(parts[1], "Название"))
                    .releaseDate(DateParser.parse(parts[2]))
                    .build();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Ошибка в строке " + line.number() + ": " + ex.getMessage(), ex);
        }
    }

    private record SourceLine(int number, String text) {
    }
}