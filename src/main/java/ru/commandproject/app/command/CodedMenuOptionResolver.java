package ru.commandproject.app.command;

import java.util.Optional;

public final class CodedMenuOptionResolver {
    private CodedMenuOptionResolver() {
    }

    public static <E extends Enum<E> & CodedMenuOption> Optional<E> fromCode(String code, E[] values) {
        if (code == null) {
            return Optional.empty();
        }

        for (E value : values) {
            if (value.code().equals(code)) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }
}
