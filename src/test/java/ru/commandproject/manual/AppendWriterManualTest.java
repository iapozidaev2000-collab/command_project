package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class ManualInputModeManualTest {

    public static void main(String[] args) {
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);

        ManualInputMode inputMode = new ManualInputMode();

        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск ручного теста: Чтение -> Коллекция -> Файл ===");

        inputMode.startCollectionInput(testCollection);


        System.out.println("\n--- Сохранение накопленных данных в файл ---");
        writer.appendCollection("Результат ручного ввода", testCollection);

        System.out.println("\n--- Тест: Запись с заголовком по умолчанию ---");
        writer.appendCollection(testCollection);

        System.out.println("\nТест завершен.");
        System.out.println("Путь к файлу: " + new File(testFilePath).getAbsolutePath());
        System.out.println("Проверьте, что в файле появились записи с заголовками 'Результат ручного ввода' и 'Отсортированная коллекция'.");
    }


    private static BookCollection<Object> createTestCollection() {
        return new BookCollection<Object>() {
            private final ArrayList<Object> storage = new ArrayList<>();

            @Override
            public void add(Object element) {
                storage.add(element);
            }

            @Override
            public Iterator<Object> iterator() {
                return storage.iterator();
            }
        };
    }
}
