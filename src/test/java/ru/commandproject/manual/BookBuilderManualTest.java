package ru.commandproject.manual;

import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.model.Book;

import java.time.LocalDate;

public final class BookBuilderManualTest extends BaseManualTest {
    public static void main(String[] args) {
        new BookBuilderManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        shouldBuildValidBook();
        shouldFailWithoutTitle();
    }

    private void shouldBuildValidBook() {
        Book book = Book.builder()
                .pages(320)
                .title("Clean Code")
                .releaseDate(LocalDate.of(2008, 8, 1))
                .build();

        assertEquals(320, book.getPages(), "Количество страниц не совпало");
        assertEquals("Clean Code", book.getTitle(), "Название не совпало");
    }

    private void shouldFailWithoutTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Book.builder()
                        .pages(100)
                        .releaseDate("2020-01-01")
                        .build(),
                "Ожидалось исключение валидации для пустого названия"
        );
    }
}
