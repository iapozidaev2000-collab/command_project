package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public final class ManualInputModeManualTest {

    public static void main(String[] args) {
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);
        Scanner scanner = new Scanner(System.in);

        ManualInputMode inputMode = new ManualInputMode();
        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск ручного теста: Чтение -> Коллекция -> Файл ===");
        
        System.out.print("Сколько книг вы хотите ввести? ");
        int count = 0;
        try {
            count = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Нужно было ввести число. Тест завершен.");
            return;
        }
        
        BookCollection<Object> result = inputMode.read(count);
        
        for (Object book : result) {
            testCollection.add(book);
        }

        System.out.println("\n--- Сохранение данных в файл ---");
        writer.appendCollection("Результат ручного ввода", testCollection);

        System.out.println("\nТест завершен.");
        System.out.println("Путь к файлу: " + new File(testFilePath).getAbsolutePath());
        System.out.println("Проверьте файл append.txt.");
    }

    private static BookCollection<Object> createTestCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();
            @Override public void add(Object element) { storage.add(element); }
            @Override public Iterator<Object> iterator() { return storage.iterator(); }
        };
    }
}
