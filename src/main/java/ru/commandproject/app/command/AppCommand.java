package ru.commandproject.app.command;

import java.util.Optional;

public enum AppCommand implements CodedMenuOption {
    LOAD_COLLECTION("1", "Загрузить коллекцию"),
    PRINT_COLLECTION("2", "Показать коллекцию"),
    SORT_COLLECTION("3", "Сортировать коллекцию"),
    SORT_EVEN_PAGES_ONLY("4", "Odd/Even сортировка по pages"),
    SAVE_COLLECTION_TO_FILE("5", "Записать коллекцию в файл"),
    COUNT_PAGES_OCCURRENCES("6", "Подсчитать вхождения pages"),
    SAVE_FOUND_VALUE_TO_FILE("7", "Записать найденное значение в файл"),
    EXIT("0", "Выход");

    private final String code;
    private final String label;

    AppCommand(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static Optional<AppCommand> fromCode(String code) {
        return CodedMenuOptionResolver.fromCode(code, values());
    }
}
