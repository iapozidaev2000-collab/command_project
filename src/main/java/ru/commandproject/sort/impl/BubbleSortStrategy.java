package ru.commandproject.sort.impl;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.List;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Book> books) {
        int n = books.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                Book current = books.get(j);
                Book next = books.get(j + 1);

                if (current.getReleaseDate().isAfter(next.getReleaseDate())) {

                    books.set(j, next);
                    books.set(j + 1, current);
                }
            }
        }
    }
}
