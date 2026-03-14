package ru.commandproject.sort.strategy;

import ru.commandproject.collection.BookCollection;

import java.util.Comparator;

public interface SortStrategy<T> {

    void sort(BookCollection<T> data, Comparator<T> comparator);
}