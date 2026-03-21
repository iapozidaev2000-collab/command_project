package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.model.Book;
import ru.commandproject.output.AppendFileWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AppendFileWriterManualTest extends BaseManualTest {
    public static void main(String[] args) {
        new AppendFileWriterManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        shouldAppendCollectionWithTitleAndNumbering();
        shouldAppendValueWithoutOverwritingPreviousContent();
        shouldWriteEmptyCollectionMessage();
        shouldUseDefaultTitlesForBlankInput();
        shouldCreateParentDirectories();
        shouldRejectBlankStringPath();
        shouldRejectNullCollection();
        shouldRejectNullValue();
        shouldRejectDirectoryPath();
    }

    private void shouldAppendCollectionWithTitleAndNumbering() {
        Path file = createTempFilePath("append-writer-collection");

        Book first = buildBook(320, "Clean Code", "2008-08-01");
        Book second = buildBook(464, "Effective Java", "2018-01-06");

        BookCollection<Book> books = new BookCollection<>();
        books.add(first);
        books.add(second);

        AppendFileWriter writer = new AppendFileWriter(file);
        writer.appendCollection("Сортировка по страницам", books);

        String content = readFile(file);

        assertTrue(content.contains("=== Сортировка по страницам ==="),
                "Файл должен содержать заголовок коллекции");
        assertTrue(content.contains("1. " + first),
                "Файл должен содержать первую книгу с номером 1");
        assertTrue(content.contains("2. " + second),
                "Файл должен содержать вторую книгу с номером 2");
    }

    private void shouldAppendValueWithoutOverwritingPreviousContent() {
        Path file = createTempFilePath("append-writer-append");

        Book first = buildBook(320, "Clean Code", "2008-08-01");
        Book second = buildBook(464, "Effective Java", "2018-01-06");

        BookCollection<Book> books = new BookCollection<>();
        books.add(first);
        books.add(second);

        AppendFileWriter writer = new AppendFileWriter(file);
        writer.appendCollection("Первая запись", books);
        writer.appendValue("Найденная книга", first);

        String content = readFile(file);

        int collectionHeaderIndex = content.indexOf("=== Первая запись ===");
        int valueHeaderIndex = content.indexOf("=== Найденная книга ===");

        assertTrue(collectionHeaderIndex >= 0,
                "Первая запись должна остаться в файле");
        assertTrue(valueHeaderIndex > collectionHeaderIndex,
                "Вторая запись должна быть добавлена после первой, а не вместо нее");
        assertTrue(content.contains(String.valueOf(first)),
                "Файл должен содержать записанное отдельное значение");
    }

    private void shouldWriteEmptyCollectionMessage() {
        Path file = createTempFilePath("append-writer-empty");

        BookCollection<Book> books = new BookCollection<>();
        AppendFileWriter writer = new AppendFileWriter(file);

        writer.appendCollection(books);

        String content = readFile(file);

        assertTrue(content.contains("=== Отсортированная коллекция ==="),
                "Для пустой коллекции должен использоваться заголовок по умолчанию");
        assertTrue(content.contains("Коллекция пуста."),
                "Файл должен содержать сообщение о пустой коллекции");
    }

    private void shouldUseDefaultTitlesForBlankInput() {
        Path file = createTempFilePath("append-writer-default-titles");

        BookCollection<Book> books = new BookCollection<>();
        books.add(buildBook(320, "Clean Code", "2008-08-01"));

        AppendFileWriter writer = new AppendFileWriter(file);
        writer.appendCollection("   ", books);
        writer.appendValue("   ", "OK");

        String content = readFile(file);

        assertTrue(content.contains("=== Отсортированная коллекция ==="),
                "Пустой заголовок коллекции должен заменяться значением по умолчанию");
        assertTrue(content.contains("=== Найденное значение ==="),
                "Пустой заголовок значения должен заменяться значением по умолчанию");
    }

    private void shouldCreateParentDirectories() {
        Path rootDirectory = createTempDirectory("append-writer-dirs");
        Path nestedFile = rootDirectory.resolve("nested").resolve("results").resolve("output.txt");

        AppendFileWriter writer = new AppendFileWriter(nestedFile);
        writer.appendValue("Проверка", "OK");

        assertTrue(Files.exists(nestedFile),
                "Writer должен создавать вложенные папки и итоговый файл");
    }

    private void shouldRejectBlankStringPath() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AppendFileWriter("   "),
                "Пустой путь к файлу должен считаться невалидным"
        );
    }

    private void shouldRejectNullCollection() {
        AppendFileWriter writer = new AppendFileWriter(createTempFilePath("append-writer-null-collection"));

        assertThrows(
                NullPointerException.class,
                () -> writer.appendCollection(null),
                "Null-коллекция должна отклоняться явно"
        );
    }

    private void shouldRejectNullValue() {
        AppendFileWriter writer = new AppendFileWriter(createTempFilePath("append-writer-null-value"));

        assertThrows(
                NullPointerException.class,
                () -> writer.appendValue(null),
                "Null-значение должно отклоняться явно"
        );
    }

    private void shouldRejectDirectoryPath() {
        Path directory = createTempDirectory("append-writer-directory");
        AppendFileWriter writer = new AppendFileWriter(directory);

        assertThrows(
                IllegalArgumentException.class,
                () -> writer.appendValue("Проверка", "OK"),
                "Запись в директорию вместо файла должна отклоняться"
        );
    }

    private Book buildBook(int pages, String title, String releaseDate) {
        return Book.builder()
                .pages(pages)
                .title(title)
                .releaseDate(releaseDate)
                .build();
    }

    private Path createTempFilePath(String prefix) {
        try {
            return Files.createTempDirectory(prefix).resolve("result.txt");
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось создать временный путь для теста", ex);
        }
    }

    private Path createTempDirectory(String prefix) {
        try {
            return Files.createTempDirectory(prefix);
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось создать временную директорию для теста", ex);
        }
    }

    private String readFile(Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось прочитать файл из теста", ex);
        }
    }
}
