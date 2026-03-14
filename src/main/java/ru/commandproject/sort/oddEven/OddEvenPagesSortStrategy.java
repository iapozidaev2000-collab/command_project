package ru.commandproject.sort.oddEven;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class OddEvenPagesSortStrategy<T> implements SortStrategy<T> {

    private final Function<T, Integer> pagesExtractor;

    public OddEvenPagesSortStrategy(Function<T, Integer> pagesExtractor) {
        this.pagesExtractor = pagesExtractor;
    }

    @Override
    public void sort(BookCollection<T> data, Comparator<T> comparator) {
        if (data == null) {
            throw new IllegalArgumentException("Коллекция не может быть null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Компаратор не может быть null!");
        }

        List<T> evenElements = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            T element = data.get(i);
            if (pagesExtractor.apply(element) % 2 == 0) {
                evenElements.add(element);
            }
        }

        evenElements.sort(Comparator.comparingInt(pagesExtractor::apply));

        int index = 0;

        for (int i = 0; i < data.size(); i++) {
            if (pagesExtractor.apply(data.get(i)) % 2 == 0) {
                data.set(i, evenElements.get(index++));
            }
        }
    }
}