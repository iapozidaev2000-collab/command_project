package ru.commandproject.sort.impl;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.List;

public class SelectionSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Book> books) {

        int n = books.size();

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                if (books.get(j).getTitle()
                        .compareTo(books.get(minIndex).getTitle()) < 0) {

                    minIndex = j;
                }
            }

            Book temp = books.get(i);
            books.set(i, books.get(minIndex));
            books.set(minIndex, temp);
        }
    }
}
