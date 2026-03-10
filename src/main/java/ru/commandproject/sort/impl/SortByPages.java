package ru.commandproject.sort.impl;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

//класс реализующий стратегию сортировки по строкам
public class SortByPages implements SortStrategy {

    // переопределенный метод который сортирует список книг по количеству страниц
    @Override
    public void sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getPages));
    }
}
