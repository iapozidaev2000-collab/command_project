package ru.commandproject.sort.oddEven;

import org.junit.jupiter.api.Test;
import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.sort.comparator.BookComparators;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OddEvenPagesSortStrategyTest {

    @Test
    void shouldSortOnlyEvenPages() {

        BookCollection<Book> collection = new BookCollection<>();

        collection.add(Book.builder()
                .title("A").pages(7).releaseDate(LocalDate.of(2021, 5, 3)).build());
        collection.add(Book.builder()
                .title("B").pages(10).releaseDate(LocalDate.of(1998, 7, 6)).build());
        collection.add(Book.builder()
                .title("C").pages(5).releaseDate(LocalDate.of(2006, 6, 7)).build());
        collection.add(Book.builder()
                .title("D").pages(2).releaseDate(LocalDate.of(2011, 11, 8)).build());
        collection.add(Book.builder()
                .title("E").pages(8).releaseDate(LocalDate.of(1988, 10, 3)).build());
        collection.add(Book.builder()
                .title("F").pages(3).releaseDate(LocalDate.of(1993, 9, 1)).build());
        collection.add(Book.builder()
                .title("G").pages(6).releaseDate(LocalDate.of(2008, 3, 6)).build());

        OddEvenPagesSortStrategy<Book> strategy = new OddEvenPagesSortStrategy<>(Book::getPages);
        strategy.sort(collection, BookComparators.BY_PAGES);

        assertEquals(7, collection.get(0).getPages());
        assertEquals(2, collection.get(1).getPages());
        assertEquals(5, collection.get(2).getPages());
        assertEquals(6, collection.get(3).getPages());
        assertEquals(8, collection.get(4).getPages());
        assertEquals(3, collection.get(5).getPages());
        assertEquals(10, collection.get(6).getPages());
    }
}