package ru.commandproject.sort.impl;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;

public class SelectionSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(BookCollection<T> data, Comparator<T> comparator) {

        int n = data.size();

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                if (comparator.compare(data.get(j), data.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            T temp = data.get(minIndex);
            data.set(minIndex, data.get(i));
            data.set(i, temp);
        }
    }
}