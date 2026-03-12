package ru.commandproject.sort.comparator;

import org.junit.jupiter.api.Test;
import ru.commandproject.model.Book;
import ru.commandproject.sort.impl.BubbleSortStrategyPages;
import ru.commandproject.sort.impl.BubbleSortStrategyTitle;
import ru.commandproject.sort.impl.BubbleSortStrategyDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookComparatorsTest {

    @Test
    void shouldSortBooksByPagesUsingContext() {

        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("Властелин колец").pages(256).releaseDate(LocalDate.of(2021, 3, 5)).build());
        books.add(Book.builder().title("Робинзон Крузо").pages(675).releaseDate(LocalDate.of(2025, 4, 9)).build());
        books.add(Book.builder().title("Унесенные ветром").pages(456).releaseDate(LocalDate.of(2018, 4, 2)).build());

        BookComparators context = new BookComparators();
        context.setSortStrategy(new BubbleSortStrategyPages());

        context.sort(books);

        assertEquals(256, books.get(0).getPages());
        assertEquals(456, books.get(1).getPages());
        assertEquals(675, books.get(2).getPages());
    }

    @Test
    void shouldSortBooksByTitleUsingContext() {

        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("Властелин колец").pages(256).releaseDate(LocalDate.of(2021, 3, 5)).build());
        books.add(Book.builder().title("Робинзон Крузо").pages(675).releaseDate(LocalDate.of(2025, 4, 9)).build());
        books.add(Book.builder().title("Унесенные ветром").pages(456).releaseDate(LocalDate.of(2018, 4, 2)).build());

        BookComparators context = new BookComparators();
        context.setSortStrategy(new BubbleSortStrategyTitle());

        context.sort(books);

        assertEquals("Властелин колец", books.get(0).getTitle());
        assertEquals("Робинзон Крузо", books.get(1).getTitle());
        assertEquals("Унесенные ветром", books.get(2).getTitle());
    }

    @Test
    void shouldSortBooksByDateUsingContext() {

        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("Властелин колец").pages(589).releaseDate(LocalDate.of(2021,2,3)).build());
        books.add(Book.builder().title("Робинзон крузо").pages(258).releaseDate(LocalDate.of(2022,4,5)).build());
        books.add(Book.builder().title("Унесенные ветром").pages(149).releaseDate(LocalDate.of(2023,6,7)).build());

        BookComparators context = new BookComparators();
        context.setSortStrategy(new BubbleSortStrategyDate());

        context.sort(books);

        assertEquals(LocalDate.of(2021,2,3), books.get(0).getReleaseDate());
        assertEquals(LocalDate.of(2022,4,5), books.get(1).getReleaseDate());
        assertEquals(LocalDate.of(2023,6,7), books.get(2).getReleaseDate());
    }

    @Test
    void shouldThrowExceptionIfStrategyNotSet() {

        List<Book> books = new ArrayList<>();
        BookComparators context = new BookComparators();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> context.sort(books));
        assertEquals("Стратегия не выбрана", exception.getMessage());
    }
}