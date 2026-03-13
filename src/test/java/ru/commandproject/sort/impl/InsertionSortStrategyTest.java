package ru.commandproject.sort.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.sort.comparator.BookComparators;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertionSortStrategyTest {

    private BookCollection<Book> books;

    @BeforeEach
    void setUp() {
        books = new BookCollection<>();

        books.add(Book.builder()
                .title("Властелин колец")
                .pages(256)
                .releaseDate(LocalDate.of(2020, 1, 14))
                .build());

        books.add(Book.builder()
                .title("Робинзон Крузо")
                .pages(675)
                .releaseDate(LocalDate.of(2022, 2, 10))
                .build());

        books.add(Book.builder()
                .title("Унесенные ветром")
                .pages(456)
                .releaseDate(LocalDate.of(2021, 3, 22))
                .build());
    }

    @Test
    void shouldSortByPages() {
        InsertionSortStrategy<Book> strategy = new InsertionSortStrategy<>();
        strategy.sort(books, BookComparators.BY_PAGES);

        assertEquals(256, books.get(0).getPages());
        assertEquals(456, books.get(1).getPages());
        assertEquals(675, books.get(2).getPages());
    }

    @Test
    void shouldSortByTitle() {
        InsertionSortStrategy<Book> strategy = new InsertionSortStrategy<>();
        strategy.sort(books, BookComparators.BY_TITLE);

        assertEquals("Властелин колец", books.get(0).getTitle());
        assertEquals("Робинзон Крузо", books.get(1).getTitle());
        assertEquals("Унесенные ветром", books.get(2).getTitle());
    }

    @Test
    void shouldSortByReleaseDate() {
        InsertionSortStrategy<Book> strategy = new InsertionSortStrategy<>();
        strategy.sort(books, BookComparators.BY_DATE);

        assertEquals(LocalDate.of(2020, 1, 14), books.get(0).getReleaseDate());
        assertEquals(LocalDate.of(2021, 3, 22), books.get(1).getReleaseDate());
        assertEquals(LocalDate.of(2022, 2, 10), books.get(2).getReleaseDate());
    }
}