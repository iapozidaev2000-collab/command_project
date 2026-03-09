# command_project

Учебный проект по сортировкам кастомного класса `Book`:
- `pages` (int)
- `title` (String)
- `releaseDate` (LocalDate)

## Документы
- Постановка задания: `docs/ASSIGNMENT.md`
- Структура проекта: `docs/PROJECT_STRUCTURE.md`
- План задач: `docs/TASKS.md`

## Запуск
```bash
mvn clean test
mvn exec:java -Dexec.mainClass=ru.commandproject.app.Main
```

Если `exec` не установлен, можно запускать из IDE класс `ru.commandproject.app.Main`.

## Запуск ручных тестов (Windows)
```bash
chcp 65001
mvn -q test
java -cp "target/classes;target/test-classes" ru.commandproject.manual.BookBuilderManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.BookCollectionManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.ValidationManualTest
java -cp "target/classes;target/test-classes" ru.commandproject.manual.AppLoopSmokeManualTest
```
