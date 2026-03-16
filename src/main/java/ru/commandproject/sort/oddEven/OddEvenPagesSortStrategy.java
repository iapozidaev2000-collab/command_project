package ru.commandproject.sort.oddEven;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.sort.impl.BubbleSortStrategy;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;
import java.util.Objects;

public final class OddEvenPagesSortStrategy implements SortStrategy<Book> {
    private final SortStrategy<Book> delegateStrategy;

    public OddEvenPagesSortStrategy() {
        this(new BubbleSortStrategy<>());
    }

    public OddEvenPagesSortStrategy(SortStrategy<Book> delegateStrategy) {
        this.delegateStrategy = Objects.requireNonNull(
                delegateStrategy,
                "Базовый алгоритм сортировки не должен быть null"
        );
    }

    public void sort(BookCollection<Book> data) {
        sort(data, null);
    }

    @Override
    public void sort(BookCollection<Book> data, Comparator<Book> comparator) {
        if (data == null) {
            throw new IllegalArgumentException("Коллекция не может быть null!");
        }

        BookCollection<Book> evenBooksCollection = new BookCollection<>();

        for (int i = 0; i < data.size(); i++) {
            Book book = data.get(i);
            if (book.getPages() % 2 == 0) {
                evenBooksCollection.add(book);
            }
        }

        delegateStrategy.sort(evenBooksCollection, comparator );

        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            Book book = data.get(i);
            if (book.getPages() % 2 == 0) {
                data.set(i, evenBooksCollection.get(index++));
            }
        }
    }
}