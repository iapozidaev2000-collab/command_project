package ru.commandproject.app;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.app.command.AppCommand;
import ru.commandproject.app.command.InputSourceCommand;
import ru.commandproject.app.command.SortAlgorithmCommand;
import ru.commandproject.app.command.SortFieldCommand;
import ru.commandproject.input.FileInputMode;
import ru.commandproject.input.InputMode;
import ru.commandproject.input.ManualInputMode;
import ru.commandproject.input.RandomInputMode;
import ru.commandproject.model.Book;
import ru.commandproject.output.AppendFileWriter;
import ru.commandproject.service.OccurrenceCounterService;
import ru.commandproject.sort.comparator.BookComparators;
import ru.commandproject.sort.impl.BubbleSortStrategy;
import ru.commandproject.sort.impl.InsertionSortStrategy;
import ru.commandproject.sort.impl.SelectionSortStrategy;
import ru.commandproject.sort.oddEven.OddEvenPagesSortStrategy;
import ru.commandproject.sort.strategy.SortStrategy;
import ru.commandproject.util.ConsoleIO;
import ru.commandproject.validation.InputValidator;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.Scanner;

public final class ApplicationLoop {
    private final Scanner scanner;
    private final PrintStream out;
    private final OccurrenceCounterService occurrenceCounterService;
    private BookCollection<Book> books;

    public ApplicationLoop(Scanner scanner) {
        this(scanner, ConsoleIO.out(), new OccurrenceCounterService());
    }

    public ApplicationLoop(Scanner scanner, PrintStream out) {
        this(scanner, out, new OccurrenceCounterService());
    }

    ApplicationLoop(Scanner scanner, PrintStream out, OccurrenceCounterService occurrenceCounterService) {
        this.scanner = scanner;
        this.out = out;
        this.occurrenceCounterService = occurrenceCounterService;
        this.books = new BookCollection<>();
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            if (!scanner.hasNextLine()) {
                out.println();
                out.println("Ввод завершен. Приложение остановлено.");
                break;
            }
            String choice = scanner.nextLine().trim();
            AppCommand command = AppCommand.fromCode(choice).orElse(null);

            try {
                if (command == null) {
                    out.println("Неизвестная команда. Повторите ввод.");
                    continue;
                }

                switch (command) {
                    case LOAD_COLLECTION -> loadCollection();
                    case PRINT_COLLECTION -> printCollection();
                    case SORT_COLLECTION -> sortCollection();
                    case SORT_EVEN_PAGES_ONLY -> sortEvenPagesOnly();
                    case SAVE_COLLECTION_TO_FILE -> saveCollectionToFile();
                    case COUNT_PAGES_OCCURRENCES -> countPagesOccurrences();
                    case EXIT -> running = false;
                }
            } catch (IllegalStateException ex) {
                out.println(ex.getMessage());
                if (ex.getMessage() != null && ex.getMessage().contains("Ввод завершен")) {
                    running = false;
                }
            } catch (IllegalArgumentException ex) {
                out.println(ex.getMessage());
            } catch (RuntimeException ex) {
                out.println("Ошибка выполнения операции: " + ex.getMessage());
            }
        }

        out.println("Выход из программы.");
    }

    private void loadCollection() {
        int count = readPositiveInt("Введите размер коллекции: ", "Размер коллекции");
        InputMode<Book> inputMode = chooseInputMode();

        books = inputMode.read(count);
        out.println("Коллекция загружена. Размер: " + books.size());
    }

    private InputMode<Book> chooseInputMode() {
        while (true) {
            out.println();
            out.println("Выберите источник данных:");
            for (InputSourceCommand command : InputSourceCommand.values()) {
                out.println(command.menuLine());
            }
            out.print("Ваш выбор: ");

            String choice = nextLineOrThrow();
            InputSourceCommand command = InputSourceCommand.fromCode(choice).orElse(null);
            if (command == null) {
                out.println("Неизвестный вариант. Повторите ввод.");
                continue;
            }

            switch (command) {
                case MANUAL:
                    return new ManualInputMode(scanner, out);
                case RANDOM:
                    return new RandomInputMode();
                case FILE:
                    String path = readNonBlankLine("Введите путь к файлу: ", "Путь к файлу");
                    return new FileInputMode(path);
            }
        }
    }

    private void printCollection() {
        if (books.isEmpty()) {
            out.println("Коллекция пуста. Сначала загрузите данные.");
            return;
        }

        out.println();
        out.println("Текущая коллекция:");
        int index = 1;
        for (Book book : books) {
            out.println(index + ". " + book);
            index++;
        }
    }

    private void sortCollection() {
        if (books.isEmpty()) {
            out.println("Коллекция пуста. Сначала загрузите данные.");
            return;
        }

        SortStrategy<Book> strategy = chooseBaseSortStrategy();
        Comparator<Book> comparator = chooseComparator();
        strategy.sort(books, comparator);
        out.println("Сортировка выполнена.");
    }

    private void sortEvenPagesOnly() {
        if (books.isEmpty()) {
            out.println("Коллекция пуста. Сначала загрузите данные.");
            return;
        }

        SortStrategy<Book> delegate = chooseBaseSortStrategy();
        OddEvenPagesSortStrategy oddEvenStrategy = new OddEvenPagesSortStrategy(delegate);
        oddEvenStrategy.sort(books);
        out.println("Odd/Even сортировка по полю pages выполнена.");
    }

    private SortStrategy<Book> chooseBaseSortStrategy() {
        while (true) {
            out.println();
            out.println("Выберите алгоритм сортировки:");
            for (SortAlgorithmCommand command : SortAlgorithmCommand.values()) {
                out.println(command.menuLine());
            }
            out.print("Ваш выбор: ");

            String choice = nextLineOrThrow();
            SortAlgorithmCommand command = SortAlgorithmCommand.fromCode(choice).orElse(null);
            if (command == null) {
                out.println("Неизвестный вариант. Повторите ввод.");
                continue;
            }

            switch (command) {
                case BUBBLE:
                    return new BubbleSortStrategy<>();
                case INSERTION:
                    return new InsertionSortStrategy<>();
                case SELECTION:
                    return new SelectionSortStrategy<>();
            }
        }
    }

    private Comparator<Book> chooseComparator() {
        while (true) {
            out.println();
            out.println("Выберите поле сортировки:");
            for (SortFieldCommand command : SortFieldCommand.values()) {
                out.println(command.menuLine());
            }
            out.print("Ваш выбор: ");

            String choice = nextLineOrThrow();
            SortFieldCommand command = SortFieldCommand.fromCode(choice).orElse(null);
            if (command == null) {
                out.println("Неизвестный вариант. Повторите ввод.");
                continue;
            }

            switch (command) {
                case PAGES:
                    return BookComparators.BY_PAGES;
                case TITLE:
                    return BookComparators.BY_TITLE;
                case RELEASE_DATE:
                    return BookComparators.BY_DATE;
            }
        }
    }

    private void saveCollectionToFile() {
        if (books.isEmpty()) {
            out.println("Коллекция пуста. Сначала загрузите данные.");
            return;
        }

        String path = readNonBlankLine("Введите путь к файлу для записи: ", "Путь к файлу");
        out.print("Введите заголовок (можно оставить пустым): ");
        String title = nextLineOrThrow();

        AppendFileWriter writer = new AppendFileWriter(path);
        writer.appendCollection(title, books);
        out.println("Коллекция записана в файл: " + path);
    }

    private void countPagesOccurrences() {
        if (books.isEmpty()) {
            out.println("Коллекция пуста. Сначала загрузите данные.");
            return;
        }

        int targetPages = readPositiveInt("Введите значение pages для поиска: ", "pages");
        int threadCount = readPositiveInt("Введите количество потоков: ", "Количество потоков");

        BookCollection<Integer> pagesCollection = new BookCollection<>();
        for (Book book : books) {
            pagesCollection.add(book.getPages());
        }

        occurrenceCounterService.countOccurrencesAndPrint(pagesCollection, targetPages, threadCount, out);
    }

    private int readPositiveInt(String prompt, String fieldName) {
        while (true) {
            out.print(prompt);
            String raw = nextLineOrThrow();
            try {
                return InputValidator.parsePositiveInt(raw, fieldName);
            } catch (IllegalArgumentException ex) {
                out.println(ex.getMessage());
            }
        }
    }

    private String readNonBlankLine(String prompt, String fieldName) {
        while (true) {
            out.print(prompt);
            String raw = nextLineOrThrow();
            try {
                return InputValidator.requireNonBlank(raw, fieldName);
            } catch (IllegalArgumentException ex) {
                out.println(ex.getMessage());
            }
        }
    }

    private String nextLineOrThrow() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Ввод завершен до окончания операции.");
        }
        return scanner.nextLine().trim();
    }

    private void printMenu() {
        out.println();
        out.println("==== Консоль книг ====");
        for (AppCommand command : AppCommand.values()) {
            out.println(command.menuLine());
        }
        out.print("Выберите пункт: ");
    }
}
