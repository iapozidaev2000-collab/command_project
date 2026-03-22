package ru.commandproject.app.command;

public interface CodedMenuOption {
    String code();

    String label();

    default String menuLine() {
        return code() + " - " + label();
    }
}
