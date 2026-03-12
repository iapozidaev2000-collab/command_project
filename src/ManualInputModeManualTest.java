package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class ManualInputModeManualTest {

    public static void main(String[] args) {
        // 1. Подготавливаем файл для записи (в папке проекта)
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);

        // 2. Создаем экземпляр режима ввода
        ManualInputMode inputMode = new ManualInputMode(writer);

        // 3. Создаем анонимную реализацию BookCollection для теста
        // Замените на вашу реальную реализацию, если она готова
        BookCollection<Object> testCollection = createTestCollection();

        System.out.println("=== Запуск ручного теста ManualInputMode ===");

        // Тест 1: Ввод одиночного значения
        System.out.println("\n--- Тест: Одиночное значение ---");
        inputMode.inputSingleValue();

        // Тест 2: Циклический ввод коллекции
        System.out.println("\n--- Тест: Ввод коллекции (пишите 'exit' для выхода) ---");
        inputMode.startCollectionInput(testCollection);

        // Тест 3: Сохранение всей накопленной коллекции
        System.out.println("\n--- Тест: Сохранение всей коллекции разом ---");
        inputMode.saveCurrentCollection("Финальный отчет", testCollection);

        System.out.println("\nТест завершен. Проверьте файл: " + new File(testFilePath).getAbsolutePath());
    }

    /**
     * Вспомогательный метод для создания простейшей коллекции
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

            // Если в вашем интерфейсе есть другие методы, их нужно переопределить здесь
        };
    }
}