package ru.commandproject.input;
import ru.commandproject.collection.BookCollection;
import ru.commandproject.output.AppendFileWriter;
import java.util.Scanner;

public class ManualInputMode {
    private final Scanner scanner;
    private final AppendFileWriter writer;

    public ManualInputMode(AppendFileWriter writer) {
        this.scanner = new Scanner(System.in);
        this.writer = writer;
    }

    /**
     * Запускает цикл ручного ввода коллекции книг
     */
    public void startCollectionInput(BookCollection<Object> collection) {
        System.out.println("Введите данные книг (для завершения введите 'exit'):");

        while (true) {
            System.out.print("Введите название или данные книги: ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) break;

            // Добавляем в коллекцию (убедитесь, что у BookCollection есть метод add)
            collection.add(input);

            // Сразу дозаписываем в файл через ваш writer
            writer.appendValue("Добавлена запись", input);
        }

        System.out.println("Ввод завершен. Коллекция сохранена.");
    }

    /**
     * Позволяет ввести одиночное значение с заголовком
     */
    public void inputSingleValue() {
        System.out.print("Введите заголовок для записи: ");
        String title = scanner.nextLine();

        System.out.print("Введите значение: ");
        String value = scanner.nextLine();

        writer.appendValue(title, value);
        System.out.println("Запись добавлена в файл.");
    }

    /**
     * Метод для записи всей текущей коллекции целиком
     */
    public void saveCurrentCollection(String title, BookCollection<?> collection) {
        writer.appendCollection(title, collection);
        System.out.println("Вся коллекция '" + title + "' успешно записана.");
    }
}
