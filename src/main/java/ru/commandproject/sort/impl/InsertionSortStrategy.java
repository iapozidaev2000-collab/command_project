package ru.commandproject.sort.impl;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;

public class InsertionSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(BookCollection<T> data, Comparator<T> comparator) {
        for (int i = 1; i < data.size(); i++) {
            T key = data.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(data.get(j), key) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, key);
        }
    }
}