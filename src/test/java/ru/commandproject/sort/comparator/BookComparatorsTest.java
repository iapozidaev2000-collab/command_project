package ru.commandproject.sort.comparator;

import org.junit.jupiter.api.Test;
import ru.commandproject.model.Book;
import ru.commandproject.sort.impl.InsertionSortStrategy;
import ru.commandproject.sort.impl.SelectionSortStrategy;
import ru.commandproject.sort.impl.BubbleSortStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookComparatorsTest {

    // тест страницы
    @Test
    void shouldSortBooksByPagesUsingContext() {
        // создание книг
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("Властелин колец").pages(256).releaseDate(LocalDate.of(2021, 3, 5)).build());
        books.add(Book.builder().title("Робинзон Крузо").pages(675).releaseDate(LocalDate.of(2025, 4, 9)).build());
        books.add(Book.builder().title("Унесенные ветром").pages(456).releaseDate(LocalDate.of(2018, 4, 2)).build());

        BookComparators context = new BookComparators();
        context.setSortStrategy(new InsertionSortStrategy());

        // сортировка
        context.sort(books);

        // должно быть так
        assertEquals(256, books.get(0).getPages());
        assertEquals(456, books.get(1).getPages());
        assertEquals(675, books.get(2).getPages());
    }

    @Test
    void shouldSortBooksByTitleUsingContext() {
        // создание книг
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("Властелин колец").pages(256).releaseDate(LocalDate.of(2021, 3, 5)).build());
        books.add(Book.builder().title("Робинзон Крузо").pages(675).releaseDate(LocalDate.of(2025, 4, 9)).build());
        books.add(Book.builder().title("Унесенные ветром").pages(456).releaseDate(LocalDate.of(2018, 4, 2)).build());

        BookComparators context = new BookComparators();
        context.setSortStrategy(new SelectionSortStrategy());

        // сортировка
        context.sort(books);

        // проверка
        assertEquals("Властелин колец", books.get(0).getTitle());
        assertEquals("Робинзон Крузо", books.get(1).getTitle());
        assertEquals("Унесенные ветром", books.get(2).getTitle());
    }

    @Test
    void shouldSortBooksByDateUsingContext() {
        // создание книг
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("Властелин колец").pages(589).releaseDate(LocalDate.of(2021,2,3)).build());
        books.add(Book.builder().title("Робинзон крузо").pages(258).releaseDate(LocalDate.of(2022,4,5)).build());
        books.add(Book.builder().title("Унесенные ветром").pages(149).releaseDate(LocalDate.of(2023,6,7)).build());

        BookComparators context = new BookComparators();
        context.setSortStrategy(new BubbleSortStrategy());

        // сортировка
        context.sort(books);

        // проверка
        assertEquals(LocalDate.of(2021,2,3), books.get(0).getReleaseDate());
        assertEquals(LocalDate.of(2022,4,5), books.get(1).getReleaseDate());
        assertEquals(LocalDate.of(2023,6,7), books.get(2).getReleaseDate());
    }

    @Test
    void shouldThrowExceptionIfStrategyNotSet() {
        // Arrange
        List<Book> books = new ArrayList<>();
        BookComparators context = new BookComparators();

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> context.sort(books));
        assertEquals("Стратегия не выбрана", exception.getMessage());
    }
}