package ru.commandproject.sort.impl;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.List;

public class InsertionSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Book> books) {
        for (int i = 1; i < books.size(); i++) {

            Book current = books.get(i);
            int j = i - 1;

            while (j >= 0 && books.get(j).getPages() > current.getPages()) {
                books.set(j + 1, books.get(j));
                j--;
            }

            books.set(j + 1, current);
        }
    }
}
