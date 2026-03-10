package org.output;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class AppendFileWriter {
    public static class Book {
        int pages;
        String title;
        int releaseDate; // Используем int для года, как в примере

        public Book(int pages, String title, int releaseDate) {
            this.pages = pages;
            this.title = title;
            this.releaseDate = releaseDate;
        }
    }
    public static void main(String[] args) {
        // Создаем список всех книг из примера
        List<Book> books = new ArrayList<Book>();
        books.add(new Book(352, "А.С. Пушкин. Капитанская дочка", 1989));
        books.add(new Book(480, "Никто, кроме тебя", 1994));
        books.add(new Book(480, "Богатые тоже плачут", 1992));
        books.add(new Book(400, "М. Ю. Лермонтов. Избранное", 2010));
        books.add(new Book(320, "Алгебра. 9 класс", 2003));
        books.add(new Book(448, "М. Пьюзо. Крёстный отец", 1998));
        books.add(new Book(496, "Б. Л. Пастернак. Доктор Живаго", 2005));
        books.add(new Book(256, "И. С. Никитин. Стихи", 1967));
        books.add(new Book(160, "И. С. Тургенев. Муму", 1990));
        books.add(new Book(496, "Б. Л. Пастернак. Доктор Живаго", 2005));
        books.add(new Book(176, "Н. В. Гоголь. Нос", 1974));
        books.add(new Book(352, "А.В. Кивинов. Менты", 1999));
        String fileName = "append.txt";
        saveBooksToFile(books, fileName);

        // Теперь этот метод будет "виден"
        printFileContent(fileName);
    }

    public static void printFileContent(String fileName) {
        BufferedReader reader = null;
        try {
            // Открываем файл для чтения
            reader = new BufferedReader(new FileReader(fileName));
            String line;

            System.out.println("\n=== Содержимое файла " + fileName + " ===");

            // Читаем файл построчно, пока не дойдем до конца
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Не удалось прочитать файл: " + e.getMessage());
        } finally {
            // Обязательно закрываем файл в блоке finally
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveBooksToFile(List<Book> books, String filename) {
        File file = new File(filename);
        boolean isNewFile = !file.exists();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            if (isNewFile) {
                writer.write("Страниц\tНаименование книги\tГод");
                writer.newLine();
            }
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                String line = book.pages + "\t" + book.title + "\t" + book.releaseDate;
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            System.out.println("Готово!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}