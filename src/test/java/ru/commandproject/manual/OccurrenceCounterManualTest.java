package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.service.OccurrenceCounterService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class OccurrenceCounterManualTest extends BaseManualTest {
    public static void main(String[] args) {
        new OccurrenceCounterManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        shouldCountOccurrencesInParallel();
        shouldReturnZeroWhenElementNotFound();
        shouldSupportNullTargetElement();
        shouldWorkWhenThreadCountGreaterThanCollectionSize();
        shouldPrintResultToConsole();
        shouldRejectNullCollection();
        shouldRejectNonPositiveThreadCount();
        shouldRejectNullOutputStream();
    }

    private void shouldCountOccurrencesInParallel() {
        BookCollection<Integer> values = new BookCollection<>();
        values.add(1);
        values.add(2);
        values.add(1);
        values.add(3);
        values.add(1);
        values.add(4);

        OccurrenceCounterService service = new OccurrenceCounterService();
        int count = service.countOccurrences(values, 1, 3);

        assertEquals(3, count, "Многопоточный подсчет должен корректно считать количество вхождений");
    }

    private void shouldReturnZeroWhenElementNotFound() {
        BookCollection<Integer> values = new BookCollection<>();
        values.add(10);
        values.add(20);
        values.add(30);

        OccurrenceCounterService service = new OccurrenceCounterService();
        int count = service.countOccurrences(values, 100, 2);

        assertEquals(0, count, "Если элемента нет, должен возвращаться 0");
    }

    private void shouldSupportNullTargetElement() {
        BookCollection<String> values = new BookCollection<>();
        values.add("A");
        values.add(null);
        values.add("B");
        values.add(null);
        values.add("C");

        OccurrenceCounterService service = new OccurrenceCounterService();
        int count = service.countOccurrences(values, null, 2);

        assertEquals(2, count, "Подсчет должен поддерживать поиск null-элемента");
    }

    private void shouldWorkWhenThreadCountGreaterThanCollectionSize() {
        BookCollection<Integer> values = new BookCollection<>();
        values.add(5);
        values.add(5);
        values.add(7);

        OccurrenceCounterService service = new OccurrenceCounterService();
        int count = service.countOccurrences(values, 5, 10);

        assertEquals(2, count, "Количество потоков больше размера коллекции не должно ломать подсчет");
    }

    private void shouldPrintResultToConsole() {
        BookCollection<Integer> values = new BookCollection<>();
        values.add(9);
        values.add(1);
        values.add(9);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);

        OccurrenceCounterService service = new OccurrenceCounterService();
        int count = service.countOccurrencesAndPrint(values, 9, 2, out);
        String output = bytes.toString(StandardCharsets.UTF_8);

        assertEquals(2, count, "Метод с печатью должен возвращать корректный результат");
        assertTrue(
                output.contains("Количество вхождений элемента \"9\": 2"),
                "Метод должен выводить результат подсчета в консоль"
        );
    }

    private void shouldRejectNullCollection() {
        OccurrenceCounterService service = new OccurrenceCounterService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.countOccurrences(null, 1, 2),
                "Null-коллекция должна отклоняться"
        );
    }

    private void shouldRejectNonPositiveThreadCount() {
        BookCollection<Integer> values = new BookCollection<>();
        values.add(1);

        OccurrenceCounterService service = new OccurrenceCounterService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.countOccurrences(values, 1, 0),
                "Количество потоков должно быть больше 0"
        );
    }

    private void shouldRejectNullOutputStream() {
        BookCollection<Integer> values = new BookCollection<>();
        values.add(1);

        OccurrenceCounterService service = new OccurrenceCounterService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.countOccurrencesAndPrint(values, 1, 1, null),
                "Null-поток вывода должен отклоняться"
        );
    }
}
