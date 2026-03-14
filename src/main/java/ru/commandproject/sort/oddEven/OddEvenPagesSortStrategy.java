package ru.commandproject.sort.oddEven;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;
import java.util.function.Function;

public class OddEvenPagesSortStrategy<T>  implements SortStrategy<T> {

    private final Function<T, Integer> pagesExtractor;

    public OddEvenPagesSortStrategy(Function<T, Integer> pagesExtractor) {
        this.pagesExtractor = pagesExtractor;
    }

    @Override
    public void sort(BookCollection<T> data, Comparator<T> comparator){
        if (data == null) {
            throw new IllegalArgumentException("Коллекция не может быть null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Компаратор не может быть null!");
        }
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = -1;
            for (int j = i; j < n; j++) {
                T element = data.get(j);
                if (pagesExtractor.apply(element) % 2 != 0) {
                    continue;
                }
                if (minIndex == -1 ||
                        comparator.compare(element, data.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != -1 && pagesExtractor.apply(data.get(i)) % 2 == 0) {
                T temp = data.get(i);
                data.set(i, data.get(minIndex));
                data.set(minIndex, temp);
            }
        }
    }
}
