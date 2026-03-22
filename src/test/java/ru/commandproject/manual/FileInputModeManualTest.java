package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.FileInputMode;
import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.model.Book;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public final class FileInputModeManualTest extends BaseManualTest {
    public static void main(String[] args) {
        new FileInputModeManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        shouldReadRequestedNumberOfBooksFromFile();
        shouldReadBooksWhenHeaderExists();
        shouldIgnoreBlankLines();
        shouldThrowWhenFileHasTooFewRecords();
        shouldThrowForInvalidLineFormat();
        shouldThrowForInvalidFieldValue();
        shouldRejectMissingFile();
    }

    private void shouldReadRequestedNumberOfBooksFromFile() {
        Path file = writeTempFile(
                "320;Clean Code;2008-08-01",
                "464;Effective Java;2018-01-06",
                "352;Domain-Driven Design;2003-08-30"
        );

        FileInputMode inputMode = new FileInputMode(file);
        BookCollection<Book> books = inputMode.read(2);

        assertEquals(2, books.size(), "Должны быть считаны только первые две книги");
        assertEquals("Clean Code", books.get(0).getTitle(), "Первая книга должна быть считана корректно");
        assertEquals(LocalDate.of(2018, 1, 6), books.get(1).getReleaseDate(),
                "Вторая книга должна быть считана корректно");
    }

    private void shouldIgnoreBlankLines() {
        Path file = writeTempFile(
                "",
                "320;Clean Code;2008-08-01",
                "   ",
                "464;Effective Java;2018-01-06"
        );

        FileInputMode inputMode = new FileInputMode(file);
        BookCollection<Book> books = inputMode.read(2);

        assertEquals(2, books.size(), "Пустые строки не должны считаться записями");
        assertEquals(320, books.get(0).getPages(), "Первая непустая строка должна быть считана");
        assertEquals(464, books.get(1).getPages(), "Вторая непустая строка должна быть считана");
    }

    private void shouldReadBooksWhenHeaderExists() {
        Path file = writeTempFile(
                "=== Коллекция книг ===",
                "320;Clean Code;2008-08-01",
                "464;Effective Java;2018-01-06"
        );

        FileInputMode inputMode = new FileInputMode(file);
        BookCollection<Book> books = inputMode.read(2);

        assertEquals(2, books.size(), "Заголовок должен игнорироваться при чтении");
        assertEquals(320, books.get(0).getPages(), "Первая книга после заголовка должна быть считана");
        assertEquals(464, books.get(1).getPages(), "Вторая книга после заголовка должна быть считана");
    }

    private void shouldThrowWhenFileHasTooFewRecords() {
        Path file = writeTempFile("320;Clean Code;2008-08-01");
        FileInputMode inputMode = new FileInputMode(file);

        assertThrows(
                IllegalStateException.class,
                () -> inputMode.read(2),
                "Если в файле меньше записей, чем запрошено, должна быть ошибка"
        );
    }

    private void shouldThrowForInvalidLineFormat() {
        Path file = writeTempFile("320;Clean Code");
        FileInputMode inputMode = new FileInputMode(file);

        assertThrows(
                IllegalArgumentException.class,
                () -> inputMode.read(1),
                "Строка с неверным количеством полей должна отклоняться"
        );
    }

    private void shouldThrowForInvalidFieldValue() {
        Path file = writeTempFile("abc;Clean Code;2008-08-01");
        FileInputMode inputMode = new FileInputMode(file);

        assertThrows(
                IllegalArgumentException.class,
                () -> inputMode.read(1),
                "Невалидные значения полей должны отклоняться"
        );
    }

    private void shouldRejectMissingFile() {
        Path missingFile = Path.of(System.getProperty("java.io.tmpdir"), "missing-books-file-" + System.nanoTime() + ".txt");
        FileInputMode inputMode = new FileInputMode(missingFile);

        assertThrows(
                IllegalArgumentException.class,
                () -> inputMode.read(1),
                "Несуществующий файл должен отклоняться"
        );
    }

    private Path writeTempFile(String... lines) {
        try {
            Path file = Files.createTempFile("books-input-", ".txt");
            Files.writeString(file, String.join(System.lineSeparator(), lines), StandardCharsets.UTF_8);
            return file;
        } catch (IOException ex) {
            throw new IllegalStateException("Не удалось подготовить временный файл для теста", ex);
        }
    }
}
