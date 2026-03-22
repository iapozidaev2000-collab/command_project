package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.RandomInputMode;
import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.model.Book;

import java.time.LocalDate;
import java.util.Random;

public final class RandomInputModeManualTest extends BaseManualTest {
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1980, 1, 1);
    private static final LocalDate MAX_RELEASE_DATE = LocalDate.of(2025, 12, 31);

    public static void main(String[] args) {
        new RandomInputModeManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        shouldGenerateRequestedNumberOfBooks();
        shouldGenerateValidBooks();
        shouldProduceSameSequenceForSameSeed();
        shouldRejectNonPositiveCount();
    }

    private void shouldGenerateRequestedNumberOfBooks() {
        RandomInputMode inputMode = new RandomInputMode(new Random(42));

        BookCollection<Book> books = inputMode.read(5);

        assertEquals(5, books.size(), "Должно быть сгенерировано 5 книг");
    }

    private void shouldGenerateValidBooks() {
        RandomInputMode inputMode = new RandomInputMode(new Random(7));

        BookCollection<Book> books = inputMode.read(10);

        for (Book book : books) {
            assertTrue(book.getPages() >= 100 && book.getPages() <= 1000,
                    "Количество страниц должно быть в заданном диапазоне");
            assertTrue(book.getTitle() != null && !book.getTitle().isBlank(),
                    "Случайное название не должно быть пустым");
            assertTrue(book.getReleaseDate() != null,
                    "Дата выхода не должна быть null");
            assertTrue(!book.getReleaseDate().isBefore(MIN_RELEASE_DATE),
                    "Дата выхода не должна быть меньше минимальной");
            assertTrue(!book.getReleaseDate().isAfter(MAX_RELEASE_DATE),
                    "Дата выхода не должна быть больше максимальной");
        }
    }

    private void shouldProduceSameSequenceForSameSeed() {
        RandomInputMode firstInputMode = new RandomInputMode(new Random(12345));
        RandomInputMode secondInputMode = new RandomInputMode(new Random(12345));

        BookCollection<Book> firstBooks = firstInputMode.read(4);
        BookCollection<Book> secondBooks = secondInputMode.read(4);

        assertEquals(firstBooks.size(), secondBooks.size(),
                "Количество сгенерированных книг должно совпадать");

        for (int i = 0; i < firstBooks.size(); i++) {
            assertEquals(firstBooks.get(i), secondBooks.get(i),
                    "При одинаковом seed должна генерироваться одинаковая последовательность");
        }
    }

    private void shouldRejectNonPositiveCount() {
        RandomInputMode inputMode = new RandomInputMode(new Random(1));

        assertThrows(
                IllegalArgumentException.class,
                () -> inputMode.read(0),
                "Количество элементов должно проходить валидацию"
        );
    }
}
