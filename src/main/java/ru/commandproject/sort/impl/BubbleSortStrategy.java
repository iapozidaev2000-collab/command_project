package ru.commandproject.sort.impl;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(BookCollection<T> data, Comparator<T> comparator) {
        if (data == null) {
            throw new IllegalArgumentException("Коллекция не может быть null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Компаратор не может быть null!");
        }
        int n = data.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                T current = data.get(j);
                T next = data.get(j + 1);

                if (comparator.compare(current, next) > 0) {

                    data.set(j, next);
                    data.set(j + 1, current);
                }
            }
        }
    }
}