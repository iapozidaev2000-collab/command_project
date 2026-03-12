package ru.commandproject.sort.impl;

import org.junit.jupiter.api.Test;
import ru.commandproject.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertionSortStrategyTest {

    // тест на сортировку количества страниц по возрастанию
    @Test
    void shouldSortBooksByPages() {

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

        InsertionSortStrategy strategy = new InsertionSortStrategy();

        // выполняем сортировку - проверка метода
        strategy.sort(books);

        // вывод - проверяем результат
        assertEquals(256, books.get(0).getPages());
        assertEquals(456, books.get(1).getPages());
        assertEquals(675, books.get(2).getPages());
    }
}