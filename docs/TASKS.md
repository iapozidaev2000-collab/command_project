# Задачи и ответственность

## 1) Базовые правила
- Ветки команды: `nazar`, `konstantin`, `ivan`.
- Интеграционная ветка: `main`.
- Одна задача = один MR.
- Каждый MR обязан содержать тесты.
- MR без тестов не принимается.
- Каждый MR должен быть воспроизводим:
  - `git checkout <branch>`
  - `mvn test`
  - запуск manual-теста, добавленного в MR

## 2) Роли по участникам
- `nazar`: база проекта, `Book` + Builder, валидация, кастомная коллекция, многопоточность, финальная интеграция.
- `konstantin`: сортировки и компараторы.
- `ivan`: ввод данных, streams, запись в файл append.

## 3) Фиксация алгоритмов сортировки
- Используем только простые алгоритмы:
  - `Bubble Sort`
  - `Insertion Sort`
  - `Selection Sort`
- Готовые библиотечные сортировки запрещены.

## 4) Декомпозиция по MR
### 4.1 Ветка `nazar`
1. MR-N1: цикл приложения и базовое меню.
2. MR-N2: `Book` + Builder + валидация.
3. MR-N3: `BookCollection<T>`.
4. MR-N4: `OccurrenceCounterService` (многопоточный подсчет вхождений `N`).
5. MR-N5: интеграция модулей в рабочий сценарий приложения.

Тесты:
- MR-N1: smoke test запуска/выхода.
- MR-N2: `BookBuilderManualTest`, `ValidationManualTest`.
- MR-N3: `BookCollectionManualTest`.
- MR-N4: `OccurrenceCounterManualTest`.
- MR-N5: `AppFlowManualTest`.

### 4.2 Ветка `konstantin`
1. MR-K1: `BubbleSortStrategy` + тест.
2. MR-K2: `InsertionSortStrategy` + тест.
3. MR-K3: `SelectionSortStrategy` + тест.
4. MR-K4: `BookComparators` по `pages`, `title`, `releaseDate` + тест.
5. MR-K5: `OddEvenPagesSortStrategy` + тест.

### 4.3 Ветка `ivan`
1. MR-I1: `AppendFileWriter` (append mode) + тест.
2. MR-I2: `ManualInputMode` + тест.
3. MR-I3: `RandomInputMode` через streams + тест.
4. MR-I4: `FileInputMode` с валидацией и streams + тест.
5. MR-I5: интеграционные тесты ввода/вывода и негативных сценариев.

## 5) Зависимости (минимум блокировок)
- MR-N1 -> старт.
- MR-N2 и MR-N3 -> после MR-N1.
- После MR-N3 можно параллельно:
  - `konstantin`: MR-K1..MR-K4 (MR-K5 после них).
  - `ivan`: MR-I1..MR-I3 (MR-I4 после N2/N3, MR-I5 после I1..I4).
- MR-N4 -> после MR-N3.
- MR-N5 -> после MR-K5, MR-I5, MR-N4.

## 6) Покрытие требований задания
- Основное:
  - цикл и выход по выбору пользователя -> MR-N1, MR-N5
  - источник данных (файл/рандом/вручную) и длина -> MR-I2, MR-I3, MR-I4
  - Strategy -> `SortStrategy` + MR-K1..MR-K3
  - Builder -> MR-N2
  - валидация, включая ввод из файла -> MR-N2, MR-I2, MR-I4
  - сортировка по 3 полям -> MR-K4 + MR-K1..MR-K3
- Доп. 1 (чет/нечет) -> MR-K5
- Доп. 2 (append запись) -> MR-I1
- Доп. 3 (streams + кастомная коллекция) -> MR-I3, MR-I4 + MR-N3
- Доп. 4 (многопоточность) -> MR-N4
- Тесты -> обязательны в каждом MR
