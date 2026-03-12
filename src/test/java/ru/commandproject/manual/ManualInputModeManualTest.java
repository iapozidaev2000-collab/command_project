package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class ManualInputModeManualTest {

    public static void main(String[] args) {
        // 1. Подготавливаем файл и писателя
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);

        // 2. Создаем режим ввода (теперь без writer в конструкторе, согласно новым правкам)
        ManualInputMode inputMode = new ManualInputMode();

        // 3. Создаем коллекцию для хранения данных в памяти
        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск ручного теста: Чтение -> Коллекция -> Файл ===");

        // ТЕСТ 1: Заполнение коллекции вручную
        // Теперь данные только попадают в testCollection, в файл пока ничего не пишется
        inputMode.startCollectionInput(testCollection);

        // ТЕСТ 2: Запись собранных данных в файл
        // Вызываем сохранение явно через writer
        System.out.println("\n--- Сохранение накопленных данных в файл ---");
        writer.appendCollection("Результат ручного ввода", testCollection);

        // ТЕСТ 3: Проверка работы констант (запись без заголовка)
        System.out.println("\n--- Тест: Запись с заголовком по умолчанию ---");
        writer.appendCollection(testCollection);

        System.out.println("\nТест завершен.");
        System.out.println("Путь к файлу: " + new File(testFilePath).getAbsolutePath());
        System.out.println("Проверьте, что в файле появились записи с заголовками 'Результат ручного ввода' и 'Отсортированная коллекция'.");
    }

    /**
     * Анонимная реализация для теста
     */
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
