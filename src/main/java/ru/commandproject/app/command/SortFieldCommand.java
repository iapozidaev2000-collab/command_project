package ru.commandproject.app.command;

import java.util.Optional;

public enum SortFieldCommand implements CodedMenuOption {
    PAGES("1", "pages"),
    TITLE("2", "title"),
    RELEASE_DATE("3", "releaseDate");

    private final String code;
    private final String label;

    SortFieldCommand(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static Optional<SortFieldCommand> fromCode(String code) {
        return CodedMenuOptionResolver.fromCode(code, values());
    }
}
