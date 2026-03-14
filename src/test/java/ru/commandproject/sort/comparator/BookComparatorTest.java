package ru.commandproject.sort.comparator;

import org.junit.jupiter.api.Test;

import ru.commandproject.model.Book;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookComparatorTest {

    private Book book(String title, int pages, LocalDate date) {
        return Book.builder()
                .title(title)
                .pages(pages)
                .releaseDate(date)
                .build();
    }

    // книга имеющая < страниц - меньше
    // книга имеющая > страниц - больше
    @Test
    void shouldComparePagesCorrectly() {

        Book book1 = book("A",100,LocalDate.of(2020,1,1));
        Book book2 = book("B",200,LocalDate.of(2021,1,1));

        assertEquals(-1, Integer.signum(BookComparators.BY_PAGES.compare(book1, book2)));
        assertEquals(1, Integer.signum(BookComparators.BY_PAGES.compare(book2, book1)));
    }

    // возвращает 0 если страницы равны
    @Test
    void shouldReturnZeroIfPagesEqual() {

        Book book1 = book("A",100,LocalDate.of(2020,1,1));
        Book book2 = book("B",100,LocalDate.of(2021,1,1));

        assertEquals(0, BookComparators.BY_PAGES.compare(book1, book2));
    }

    // сортировка по алфавиту
    @Test
    void shouldCompareTitlesCorrectly() {

        Book book1 = book("A",100,LocalDate.of(2020,1,1));
        Book book2 = book("B",200,LocalDate.of(2021,1,1));

        assertEquals(-1, Integer.signum(BookComparators.BY_TITLE.compare(book1, book2)));
    }

    // сортировка по дате
    @Test
    void shouldCompareDatesCorrectly() {

        Book book1 = book("A",100,LocalDate.of(2020,1,1));
        Book book2 = book("B",200,LocalDate.of(2021,1,1));

        assertEquals(-1, Integer.signum(BookComparators.BY_DATE.compare(book1, book2)));
    }
}