package ru.commandproject.manual;

import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.validation.InputValidator;

public final class ValidationManualTest extends BaseManualTest {
    public static void main(String[] args) {
        new ValidationManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        int pages = InputValidator.parsePositiveInt("120", "Количество страниц");
        assertEquals(120, pages, "Число страниц должно быть корректно распознано");

        assertThrows(
                IllegalArgumentException.class,
                () -> InputValidator.parsePositiveInt("-1", "Количество страниц"),
                "Отрицательное значение страниц должно приводить к ошибке"
        );
    }
}
