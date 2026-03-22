package ru.commandproject.app.command;

import java.util.Optional;

public enum SortAlgorithmCommand implements CodedMenuOption {
    BUBBLE("1", "Bubble Sort"),
    INSERTION("2", "Insertion Sort"),
    SELECTION("3", "Selection Sort");

    private final String code;
    private final String label;

    SortAlgorithmCommand(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static Optional<SortAlgorithmCommand> fromCode(String code) {
        return CodedMenuOptionResolver.fromCode(code, values());
    }
}
