package org.output;
import java.util.ArrayList; // Нужно добавить
import java.util.List;      // Нужно добавить

public class AppendWriterManualTest {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК ТЕСТА ПРИЛОЖЕНИЯ ===");

        // Используем тот же файл
        String fileName = "append.txt";

        // 1. Создаем тестовый набор данных (отличный от основного, чтобы заметить разницу)
        List<AppendFileWriter.Book> testBooks = new ArrayList<AppendFileWriter.Book>();
        testBooks.add(new AppendFileWriter.Book(999, "ТЕСТОВАЯ КНИГА: Проверка записи", 2025));
        testBooks.add(new AppendFileWriter.Book(10, "Краткий справочник тестера", 2026));

        System.out.println("Шаг 1: Записываем тестовые данные в " + fileName);

        // 2. Вызываем метод сохранения из основного класса
        // Так как там стоит FileWriter(file, false), старые данные затрутся тестовыми
        AppendFileWriter.saveBooksToFile(testBooks, fileName);

        System.out.println("Шаг 2: Читаем файл через основной метод вывода");

        // 3. Вызываем метод чтения из основного класса
        // ВАЖНО: убедитесь, что в AppendFileWriter этот метод теперь PUBLIC
        AppendFileWriter.printFileContent(fileName);

        System.out.println("\n=== ТЕСТ ЗАВЕРШЕН ===");
    }
}