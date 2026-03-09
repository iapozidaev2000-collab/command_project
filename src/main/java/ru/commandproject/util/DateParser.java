package ru.commandproject.util;

import ru.commandproject.validation.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class DateParser {
    private DateParser() {
    }

    public static LocalDate parse(String text) {
        String normalized = InputValidator.requireNonBlank(text, "Дата выхода");
        try {
            return LocalDate.parse(normalized);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Поле \"Дата выхода\" должно быть в формате yyyy-MM-dd", ex);
        }
    }
}
