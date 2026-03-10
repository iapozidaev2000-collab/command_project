package ru.commandproject.sort.impl;

import org.junit.jupiter.api.Test;
import ru.commandproject.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortByTitleTest {

    @Test
    void shouldSortBooksByTitle() {

        // подготовка данных - создает список книг
        List<Book> books = new ArrayList<>();

        books.add(Book.builder()
                .title("Властелин колец")
                .pages(256)
                .releaseDate(LocalDate.of(2020,1,14))
                .build());

        books.add(Book.builder()
                .title("Унесенные ветром")
                .pages(456)
                .releaseDate(LocalDate.of(2021,3,22))
                .build());

        books.add(Book.builder()
                .title("Робинзон крузо")
                .pages(675)
                .releaseDate(LocalDate.of(2022,2,10))
                .build());

        SortByTitle strategy = new SortByTitle();

        // сортируем книги по названию
        strategy.sort(books);

        // проверяем порядок
        assertEquals("Властелин колец", books.get(0).getTitle());
        assertEquals("Робинзон крузо", books.get(1).getTitle());
        assertEquals("Унесенные ветром", books.get(2).getTitle());
    }

    // тест на пустой список
    @Test
    void shouldHandleEmptyList() {
        List<Book> books = new ArrayList<>();
        SortByTitle strategy = new SortByTitle();
        strategy.sort(books);
        assertEquals(0, books.size());
    }
}