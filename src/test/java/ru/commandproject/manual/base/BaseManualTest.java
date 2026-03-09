package ru.commandproject.manual.base;

import ru.commandproject.util.ConsoleIO;

import java.util.Objects;

public abstract class BaseManualTest {
    @FunctionalInterface
    protected interface ThrowingAction {
        void run();
    }

    public final void runAndReport() {
        runTests();
        ConsoleIO.out().println("Тест " + getClass().getSimpleName() + ": УСПЕШНО");
    }

    protected abstract void runTests();

    protected void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    protected void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new IllegalStateException(
                    message + " (ожидалось: " + expected + ", фактически: " + actual + ")"
            );
        }
    }

    protected void assertThrows(
            Class<? extends Throwable> expectedException,
            ThrowingAction action,
            String message
    ) {
        try {
            action.run();
        } catch (Throwable actual) {
            if (expectedException.isInstance(actual)) {
                return;
            }
            throw new IllegalStateException(
                    message + " (ожидалось исключение " + expectedException.getSimpleName()
                            + ", получено " + actual.getClass().getSimpleName() + ")"
            );
        }
        throw new IllegalStateException(
                message + " (ожидалось исключение " + expectedException.getSimpleName() + ")"
        );
    }
}
