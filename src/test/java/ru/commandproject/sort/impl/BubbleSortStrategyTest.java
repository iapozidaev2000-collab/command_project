package ru.commandproject.sort.impl;

import org.junit.jupiter.api.Test;
import ru.commandproject.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BubbleSortStrategyTest {

    // тест на книги упорядоченные по дате выхода
    @Test
    void shouldSortBooksByReleaseDate() {

        // подготовка данных - создает список книг
        List<Book> books = new ArrayList<>();

        books.add(Book.builder()
                .title("Книга 1")
                .pages(256)
                .releaseDate(LocalDate.of(2022, 2, 10))
                .build());

        books.add(Book.builder()
                .title("Книга 2")
                .pages(456)
                .releaseDate(LocalDate.of(2020, 1, 14))
                .build());

        books.add(Book.builder()
                .title("Книга 3")
                .pages(675)
                .releaseDate(LocalDate.of(2021, 3, 22))
                .build());

        BubbleSortStrategy strategy = new BubbleSortStrategy();

        // выполняем сортировку - проверка метода
        strategy.sort(books);

        // вывод - проверяем результат
        assertEquals(LocalDate.of(2020,1,14), books.get(0).getReleaseDate());
        assertEquals(LocalDate.of(2021,3,22), books.get(1).getReleaseDate());
        assertEquals(LocalDate.of(2022,2,10), books.get(2).getReleaseDate());
    }

    // тест на пустой список
    @Test
    void shouldHandleEmptyList() {
        List<Book> books = new ArrayList<>();

        BubbleSortStrategy strategy = new BubbleSortStrategy();
        strategy.sort(books);

        assertEquals(0, books.size());
    }

    // тест на один элемент
    @Test
    void shouldHandleSingleBook() {

        List<Book> books = new ArrayList<>();

        books.add(Book.builder()
                .title("Название")
                .pages(100)
                .releaseDate(LocalDate.of(2023,1,1))
                .build());

        BubbleSortStrategy strategy = new BubbleSortStrategy();
        strategy.sort(books);

        assertEquals(1, books.size());
    }
}