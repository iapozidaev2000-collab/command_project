package ru.commandproject.app.command;

import java.util.Optional;

public enum InputSourceCommand implements CodedMenuOption {
    MANUAL("1", "Ручной ввод"),
    RANDOM("2", "Случайные данные"),
    FILE("3", "Загрузка из файла");

    private final String code;
    private final String label;

    InputSourceCommand(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static Optional<InputSourceCommand> fromCode(String code) {
        return CodedMenuOptionResolver.fromCode(code, values());
    }
}
