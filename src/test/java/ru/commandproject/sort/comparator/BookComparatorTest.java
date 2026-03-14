package ru.commandproject.sort.comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.sort.impl.BubbleSortStrategy;
import ru.commandproject.sort.strategy.SortStrategy;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookComparatorTest {

    private BookCollection<Book> books;
    private SortStrategy<Book> strategy;

    @BeforeEach
    void setUp() {
        strategy = new BubbleSortStrategy<>();
        books = new BookCollection<>();

        books.add(Book.builder()
                .title("Властелин колец").pages(256).releaseDate(LocalDate.of(2020, 1, 14)).build());
        books.add(Book.builder()
                .title("Робинзон Крузо").pages(675).releaseDate(LocalDate.of(2022, 2, 10)).build());
        books.add(Book.builder()
                .title("Унесенные ветром").pages(456).releaseDate(LocalDate.of(2021, 3, 22)).build());
    }

    // сортировка по страницам
    @Test
    void shouldCompareByPages() {
        strategy.sort(books, BookComparators.BY_PAGES);

        assertEquals(256, books.get(0).getPages());
        assertEquals(456, books.get(1).getPages());
        assertEquals(675, books.get(2).getPages());
    }

    // сортировка по названию
    @Test
    void shouldCompareByTitle() {
        strategy.sort(books, BookComparators.BY_TITLE);

        assertEquals("Властелин колец", books.get(0).getTitle());
        assertEquals("Робинзон Крузо", books.get(1).getTitle());
        assertEquals("Унесенные ветром", books.get(2).getTitle());
    }

    // сортировка по дате
    @Test
    void shouldCompareByReleaseDate() {
        strategy.sort(books, BookComparators.BY_DATE);

        assertEquals(LocalDate.of(2020, 1, 14), books.get(0).getReleaseDate());
        assertEquals(LocalDate.of(2021, 3, 22), books.get(1).getReleaseDate());
        assertEquals(LocalDate.of(2022, 2, 10), books.get(2).getReleaseDate());
    }
}