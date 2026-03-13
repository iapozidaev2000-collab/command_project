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
        File file = new File(testFilePath);
        AppendFileWriter writer = new AppendFileWriter(testFilePath);
        ManualInputMode inputMode = new ManualInputMode();

        if (file.exists()) {
            System.out.println("Исходный размер файла: " + file.length() + " байт");
        }

        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск теста: Валидация -> Чтение -> Файл ===");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите проверочное слово (не должно быть пустым): ");
        String validationInput = scanner.nextLine();

        if (validationInput == null || validationInput.trim().isEmpty()) {
            System.out.println("Ошибка: Данные не прошли валидацию. Тест прерван.");
            return;
        }

        testCollection.add(validationInput);
        inputMode.startCollectionInput(testCollection);

        System.out.println("\n--- Сохранение данных в файл ---");
        writer.appendCollection("Результат ручного ввода", testCollection);
        writer.appendCollection(testCollection);

        System.out.println("\nТест завершен.");

        if (file.exists()) {
            System.out.println("Путь к файлу: " + file.getAbsolutePath());
            System.out.println("Финальный размер файла: " + file.length() + " байт");
        }
    }

    private static BookCollection<Object> createTestCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();

            @Override
            public void add(Object element) {
                if (element != null) {
                    storage.add(element);
                }
            }

            @Override
            public Iterator<Object> iterator() {
                return storage.iterator();
            }
        };
    }
}
