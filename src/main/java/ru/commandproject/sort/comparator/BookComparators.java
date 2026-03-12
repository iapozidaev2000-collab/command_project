package ru.commandproject.sort.comparator;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.List;

public class BookComparators {

    private SortStrategy sortStrategy;

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void sort(List<Book> books) {
        if (sortStrategy == null) {
            throw new IllegalStateException("Стратегия не выбрана");
        }
        sortStrategy.sort(books);
    }
}
