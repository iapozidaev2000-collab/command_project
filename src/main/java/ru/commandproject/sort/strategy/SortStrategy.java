package ru.commandproject.sort.strategy;

import ru.commandproject.model.Book;

import java.util.List;

// интерфейс, который будет реализовать паттерн стратегии
public interface SortStrategy {

    // метод сортировки который ничего не будет сортировать только возвращать
    void sort(List<Book> books);
}
