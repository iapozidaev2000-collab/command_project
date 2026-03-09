# Структура проекта

## 1) Технологический базис
- Язык: Java
- Сборка: Maven
- Пакет проекта: `ru.commandproject`

## 2) Целевая структура папок
```text
command_project/
├─ docs/
│  ├─ ASSIGNMENT.md
│  ├─ PROJECT_STRUCTURE.md
│  └─ TASKS.md
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ ru/commandproject/
│  │  │     ├─ app/
│  │  │     │  ├─ Main.java
│  │  │     │  └─ ApplicationLoop.java
│  │  │     ├─ model/
│  │  │     │  └─ Book.java
│  │  │     ├─ collection/
│  │  │     │  └─ BookCollection.java
│  │  │     ├─ validation/
│  │  │     │  ├─ BookValidator.java
│  │  │     │  └─ InputValidator.java
│  │  │     ├─ sort/
│  │  │     │  ├─ strategy/
│  │  │     │  │  └─ SortStrategy.java
│  │  │     │  ├─ comparator/
│  │  │     │  │  └─ BookComparators.java
│  │  │     │  ├─ impl/
│  │  │     │  │  ├─ BubbleSortStrategy.java
│  │  │     │  │  ├─ InsertionSortStrategy.java
│  │  │     │  │  └─ SelectionSortStrategy.java
│  │  │     │  └─ oddEven/
│  │  │     │     └─ OddEvenPagesSortStrategy.java
│  │  │     ├─ input/
│  │  │     │  ├─ InputMode.java
│  │  │     │  ├─ ManualInputMode.java
│  │  │     │  ├─ FileInputMode.java
│  │  │     │  └─ RandomInputMode.java
│  │  │     ├─ output/
│  │  │     │  └─ AppendFileWriter.java
│  │  │     ├─ service/
│  │  │     │  ├─ SortService.java
│  │  │     │  └─ OccurrenceCounterService.java
│  │  │     └─ util/
│  │  │        └─ DateParser.java
│  │  └─ resources/
│  │     └─ input/
│  └─ test/
│     └─ java/
│        └─ ru/commandproject/
│           ├─ manual/
│           │  ├─ BookBuilderManualTest.java
│           │  ├─ BookCollectionManualTest.java
│           │  ├─ ValidationManualTest.java
│           │  ├─ SortingManualTest.java
│           │  ├─ InputModesManualTest.java
│           │  ├─ AppendWriterManualTest.java
│           │  └─ OccurrenceCounterManualTest.java
│           └─ integration/
│              └─ AppFlowManualTest.java
├─ .gitignore
├─ pom.xml
└─ README.md
```

## 3) Обязательные контракты (не менять без согласования)
### 3.1 SortStrategy
```java
public interface SortStrategy<T> {
    void sort(BookCollection<T> data, java.util.Comparator<T> comparator);
}
```

### 3.2 InputMode
```java
public interface InputMode<T> {
    BookCollection<T> read(int count);
}
```

### 3.3 Builder для Book
```java
Book book = Book.builder()
    .pages(320)
    .title("Clean Code")
    .releaseDate("2008-08-01")
    .build();
```
