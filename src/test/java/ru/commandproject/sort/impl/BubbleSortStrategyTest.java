package ru.commandproject.sort.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.sort.comparator.BookComparators;
import ru.commandproject.sort.strategy.SortStrategy;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BubbleSortStrategyTest {

    private BookCollection<Book> books;
    private SortStrategy<Book> strategy;

    @BeforeEach
    void setUp() {
        strategy = new BubbleSortStrategy<>();
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

    //сортировка по страницам
    @Test
    void shouldSortByPages() {
        strategy.sort(books, BookComparators.BY_PAGES);

        assertEquals(256, books.get(0).getPages());
        assertEquals(456, books.get(1).getPages());
        assertEquals(675, books.get(2).getPages());
    }

    // сортировка по названию
    @Test
    void shouldSortByTitle() {
        strategy.sort(books, BookComparators.BY_TITLE);

        assertEquals("Властелин колец", books.get(0).getTitle());
        assertEquals("Робинзон Крузо", books.get(1).getTitle());
        assertEquals("Унесенные ветром", books.get(2).getTitle());
    }

    // сортировка по дате
    @Test
    void shouldSortByReleaseDate() {
        strategy.sort(books, BookComparators.BY_DATE);

        assertEquals(LocalDate.of(2020, 1, 14), books.get(0).getReleaseDate());
        assertEquals(LocalDate.of(2021, 3, 22), books.get(1).getReleaseDate());
        assertEquals(LocalDate.of(2022, 2, 10), books.get(2).getReleaseDate());
    }

    // сортировка пустой коллекции не ломается
    @Test
    void shouldHandleEmptyCollection() {
        BookCollection<Book> emptyBooks = new BookCollection<>();
        strategy.sort(emptyBooks, BookComparators.BY_PAGES);

        assertEquals(0, emptyBooks.size());
    }

    // сортировка коллекции с одним элементом
    @Test
    void shouldHandleSingleBook() {
        BookCollection<Book> singleBook = new BookCollection<>();
        singleBook.add(Book.builder()
                .title("Единственная книга")
                .pages(100)
                .releaseDate(LocalDate.of(2023, 1, 1))
                .build());

        strategy.sort(singleBook, BookComparators.BY_PAGES);

        assertEquals(1, singleBook.size());
        assertEquals("Единственная книга", singleBook.get(0).getTitle());
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