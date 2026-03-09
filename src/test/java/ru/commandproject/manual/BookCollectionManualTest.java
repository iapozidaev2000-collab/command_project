package ru.commandproject.manual;

import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.collection.BookCollection;

public final class BookCollectionManualTest extends BaseManualTest {
    public static void main(String[] args) {
        new BookCollectionManualTest().runAndReport();
    }

    @Override
    protected void runTests() {
        BookCollection<Integer> collection = new BookCollection<>();
        collection.add(10);
        collection.add(20);
        collection.add(30);

        assertEquals(3, collection.size(), "Размер должен быть равен 3");
        assertEquals(20, collection.get(1), "Элемент с индексом 1 должен быть равен 20");

        collection.set(1, 25);
        assertEquals(25, collection.get(1), "После set элемент с индексом 1 должен быть равен 25");

        assertEquals(2, collection.indexOf(30), "indexOf должен вернуть 2");
    }
}
