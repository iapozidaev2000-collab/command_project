package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ManualInputMode implements InputMode<Object> {
    private final Scanner scanner;

    public ManualInputMode() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public BookCollection<Object> read(int count) {
        BookCollection<Object> collection = createCollection();
        System.out.println("Режим ручного ввода. Нужно ввести элементов: " + count);
        int added = 0;
        while (added < count) {
            System.out.print("[" + (added + 1) + "/" + count + "] > ");
            String input = scanner.nextLine();
            if (input == null || "exit".equalsIgnoreCase(input.trim())) break;
            if (input.isBlank()) continue;

            collection.add(input);
            added++;
        }
        return collection;
    }

    private BookCollection<Object> createCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object e) { storage.add(e); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }

    public void startCollectionInput(BookCollection<Object> testCollection) {

    }
}
