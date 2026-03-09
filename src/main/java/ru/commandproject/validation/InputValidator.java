package ru.commandproject.validation;

public final class InputValidator {
    private InputValidator() {
    }

    public static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Поле \"" + fieldName + "\" не должно быть пустым");
        }
        return value.trim();
    }

    public static int requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException("Поле \"" + fieldName + "\" должно быть больше 0");
        }
        return value;
    }

    public static int parsePositiveInt(String value, String fieldName) {
        String normalized = requireNonBlank(value, fieldName);
        try {
            int number = Integer.parseInt(normalized);
            return requirePositive(number, fieldName);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Поле \"" + fieldName + "\" должно быть целым числом", ex);
        }
    }
}
