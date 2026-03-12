package ru.commandproject.sort.strategy;

import ru.commandproject.model.Book;

import java.util.List;

public interface SortStrategy {

    void sort(List<Book> books);
}
