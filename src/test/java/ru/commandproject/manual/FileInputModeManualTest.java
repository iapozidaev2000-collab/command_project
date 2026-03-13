package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.output.AppendFileWriter;

import java.io.File;

public final class FileInputModeManualTest {

    public static void main(String[] args) {
        String testFilePath = "append.txt";
        AppendFileWriter writer = new AppendFileWriter(testFilePath);

        // Теперь работаем через интерфейсный метод read
        ManualInputMode inputMode = new ManualInputMode();

        System.out.println("=== Запуск ручного теста: Чтение -> Коллекция -> Файл ===");

        // Вызываем метод read(count), который теперь является основным
        // Укажите количество элементов, которые хотите ввести (например, 3)
        BookCollection<Object> testCollection = inputMode.read(3);

        System.out.println("\n--- Сохранение накопленных данных в файл ---");
        writer.appendCollection("Результат ручного ввода", testCollection);

        System.out.println("\n--- Тест: Запись с заголовком по умолчанию ---");
        writer.appendCollection(testCollection);

        System.out.println("\nТест завершен.");
        System.out.println("Путь к файлу: " + new File(testFilePath).getAbsolutePath());
        System.out.println("Проверьте файл на наличие записей.");
    }
}