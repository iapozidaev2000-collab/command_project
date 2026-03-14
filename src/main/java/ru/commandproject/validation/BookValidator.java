package ru.commandproject.validation;

import java.time.LocalDate;

public final class BookValidator {
    private BookValidator() {
    }

    public static void validateForBuild(Integer pages, String title, LocalDate releaseDate) {
        if (pages == null) {
            throw new IllegalArgumentException("Поле \"Количество страниц\" обязательно");
        }
        InputValidator.requirePositive(pages, "Количество страниц");
        InputValidator.requireNonBlank(title, "Название");

        if (releaseDate == null) {
            throw new IllegalArgumentException("Поле \"Дата выхода\" обязательно");
        }
    }
}