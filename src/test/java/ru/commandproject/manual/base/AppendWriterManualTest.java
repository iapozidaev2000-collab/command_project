package ru.commandproject.manual;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.manual.base.BaseManualTest;
import ru.commandproject.output.AppendFileWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class AppendWriterManualTest extends BaseManualTest {


    public static void main(String[] args) {
        new AppendWriterManualTest().runAndReport();
    }
    @Override
    protected void runTests() {
        String testFileName = "manual_test_output.txt";
        File file = new File(testFileName);

        // 1. Очищаем файл перед тестом
        if (file.exists()) file.delete();

        try {
            // 2. Подготовка данных
            // Допустим, BookCollection принимает объекты Book или String
            BookCollection<Object> collection = new BookCollection<>();
            // collection.add(...);

            AppendFileWriter writer = new AppendFileWriter(testFileName);

            System.out.println("--- Запуск теста записи ---");

            // 3. Тестируем запись одиночного значения
            writer.appendValue("Версия теста", 1.0);

            // 4. Тестируем запись коллекции с заголовком
            writer.appendCollection("Моя библиотека", collection);

            // 5. Читаем файл и проверяем, что там внутри
            printFileResult(testFileName);

        } finally {

            file.delete();
        }
    }

    private void printFileResult(String fileName) {
        System.out.println("\n--- СОДЕРЖИМОЕ ФАЙЛА ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении результата: " + e.getMessage());
        }
        System.out.println("------------------------");
    }
}