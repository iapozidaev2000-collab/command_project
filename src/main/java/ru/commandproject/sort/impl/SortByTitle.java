package ru.commandproject.sort.impl;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

// класс реализующий сортировку книг по названию
public class SortByTitle implements SortStrategy {

    // сортирует список книг по названию
    @Override
    public void sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getTitle));
    }
}
