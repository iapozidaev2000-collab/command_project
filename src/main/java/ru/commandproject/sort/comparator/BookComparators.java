package ru.commandproject.sort.comparator;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.List;

// класс обработки данных (контекст паттерна стратегия)
public class BookComparators {

    private SortStrategy sortStrategy;

    //метод устанавливающий алгоритм сортировки
    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    // метод, который сортирует список книг
    public void sort(List<Book> books) {
        if (sortStrategy == null) {
            throw new IllegalStateException("Стратегия не выбрана");
        }
        sortStrategy.sort(books);
    }
}
