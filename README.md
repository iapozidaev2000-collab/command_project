# Book Sorting Console App

Консольное Java-приложение для сортировки кастомного класса `Book`:
- `pages` (число)
- `title` (строка)
- `releaseDate` (дата)

Постановка задания: `docs/ASSIGNMENT.md`.

## Что реализовано
- Циклическое меню приложения с явным выходом по выбору пользователя.
- Заполнение коллекции тремя способами: вручную, случайно, из файла.
- Паттерн `Strategy` для выбора алгоритма сортировки.
- `Builder` для класса `Book`.
- Валидация пользовательского ввода и данных из файла.
- Сортировка по всем 3 полям (`pages`, `title`, `releaseDate`) через компараторы.
- Алгоритмы сортировки:
  - Bubble Sort
  - Insertion Sort
  - Selection Sort
- Дополнительная odd/even сортировка по `pages`:
  - четные значения сортируются по возрастанию,
  - нечетные остаются на исходных позициях.
- Запись результатов в файл в режиме добавления (`append`):
  - коллекции,
  - найденного значения (результата подсчета вхождений).
- Многопоточный подсчет количества вхождений значения `N` в коллекцию.
- Кастомная коллекция `BookCollection<T>`.
- Заполнение через streams в соответствующих режимах ввода.

## Требования
- Java 17+
- Maven 3.9+

## Формат входного файла
Поддерживается формат:
```text
pages;title;releaseDate
```
Пример:
```text
320;Clean Code;2008-08-01
464;Effective Java;2018-01-06
```
Допустимы:
- пустые строки;
- строка-заголовок вида `=== ... ===` (будет пропущена при чтении).

## Сборка и тесты
```bash
mvn clean test
```

## Запуск приложения

### PowerShell (Windows)
```powershell
chcp 65001
[Console]::InputEncoding  = [System.Text.UTF8Encoding]::new($false)
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)

java --% -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -cp target/classes ru.commandproject.app.Main
```

## Ручные тесты (при необходимости)
```bash
java -cp "target/classes;target/test-classes" ru.commandproject.manual.BookBuilderManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.BookCollectionManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.ValidationManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.RandomInputModeManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.ManualInputModeManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.FileInputModeManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.AppendFileWriterManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.OccurrenceCounterManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.AppLoopSmokeManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.AppFlowManualTest
```
