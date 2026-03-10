package ru.commandproject.sort.impl;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

// класс реализующий стратегию сортировки по дате
public class SortByDate implements SortStrategy {

    // переопределенный интерфейс, сортировка по дате
    @Override
    public void sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getReleaseDate));
    }
}
