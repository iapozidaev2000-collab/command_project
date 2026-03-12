package ru.commandproject.sort.comparator;

import ru.commandproject.model.Book;
import ru.commandproject.sort.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

public class BookComparators {

    public static final Comparator<Book> BY_TITLE =
            Comparator.comparing(Book::getTitle);

    public static final Comparator<Book> BY_PAGES =
            Comparator.comparingInt(Book::getPages);

    public static final Comparator<Book> BY_DATE =
            Comparator.comparing(Book::getReleaseDate);
}
