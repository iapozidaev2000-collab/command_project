package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.model.Book;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Scanner;

public final class ManualInputModeManualTest extends BaseManualTest {
    public static void main(String[] args) {
        new ManualInputModeManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        shouldReadRequestedNumberOfBooks();
        shouldRepeatPromptUntilValidValueIsEntered();
        shouldRejectNonPositiveCount();
        shouldFailIfInputEndsTooEarly();
    }

    private void shouldReadRequestedNumberOfBooks() {
        String input = String.join(System.lineSeparator(),
                "320",
                "Clean Code",
                "2008-08-01",
                "464",
                "Effective Java",
                "2018-01-06"
        ) + System.lineSeparator();

        ManualInputMode inputMode = new ManualInputMode(new Scanner(input), silentOut());
        BookCollection<Book> books = inputMode.read(2);

        assertEquals(2, books.size(), "Должны быть считаны две книги");
        assertEquals("Clean Code", books.get(0).getTitle(), "Первая книга должна быть считана корректно");
        assertEquals(320, books.get(0).getPages(), "Количество страниц первой книги должно совпадать");
        assertEquals(LocalDate.of(2018, 1, 6), books.get(1).getReleaseDate(),
                "Дата второй книги должна быть считана корректно");
    }

    private void shouldRepeatPromptUntilValidValueIsEntered() {
        String input = String.join(System.lineSeparator(),
                "abc",
                "0",
                "320",
                "   ",
                "Clean Code",
                "2024/01/01",
                "2024-01-01"
        ) + System.lineSeparator();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ManualInputMode inputMode = new ManualInputMode(
                new Scanner(input),
                new PrintStream(output, true, StandardCharsets.UTF_8)
        );

        BookCollection<Book> books = inputMode.read(1);
        String console = output.toString(StandardCharsets.UTF_8);

        assertEquals(1, books.size(), "После повторных попыток должна быть считана одна книга");
        assertEquals("Clean Code", books.get(0).getTitle(), "После невалидного ввода название должно быть считано");
        assertEquals(LocalDate.of(2024, 1, 1), books.get(0).getReleaseDate(),
                "После невалидной даты должна быть считана корректная дата");
        assertTrue(console.contains("Поле \"Количество страниц\" должно быть целым числом"),
                "Должно быть сообщение об ошибке для нечисловых страниц");
        assertTrue(console.contains("Поле \"Количество страниц\" должно быть больше 0"),
                "Должно быть сообщение об ошибке для неположительных страниц");
        assertTrue(console.contains("Поле \"Название\" не должно быть пустым"),
                "Должно быть сообщение об ошибке для пустого названия");
        assertTrue(console.contains("Поле \"Дата выхода\" должно быть в формате yyyy-MM-dd"),
                "Должно быть сообщение об ошибке для некорректной даты");
    }

    private void shouldRejectNonPositiveCount() {
        ManualInputMode inputMode = new ManualInputMode(new Scanner(""), silentOut());

        assertThrows(
                IllegalArgumentException.class,
                () -> inputMode.read(0),
                "Количество элементов должно проходить валидацию"
        );
    }

    private void shouldFailIfInputEndsTooEarly() {
        String input = String.join(System.lineSeparator(),
                "320",
                "Clean Code"
        ) + System.lineSeparator();

        ManualInputMode inputMode = new ManualInputMode(new Scanner(input), silentOut());

        assertThrows(
                IllegalStateException.class,
                () -> inputMode.read(1),
                "Если ввод закончился раньше времени, должна быть явная ошибка"
        );
    }

    private PrintStream silentOut() {
        return new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8);
    }
}
