package ru.commandproject.validation;

import java.time.LocalDate;

// класс для сортировки правильности введенных данных
public final class BookValidator {
    private BookValidator() {
    }

    // проверка количества страниц на 0
    public static void validatePages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Количество страниц не должно быть <= 0!");
        }
    }
    // проверка названия книг на null, на пустую строку, на строку из пробелов.
    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название книг не корректно!");
        }
    }

    // Проверка на null
    public static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Дата не корректна!");
        }
    }

    // это метод класса, который проверяет корректность данных для создания объекта Book
    public static boolean isValid(int pages, String title, LocalDate releaseDate) {
        return pages > 0 && title != null && !title.isBlank() && releaseDate != null;
    }
}
