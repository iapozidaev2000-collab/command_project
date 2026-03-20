package ru.commandproject.service;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.util.ConsoleIO;
import ru.commandproject.validation.InputValidator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class OccurrenceCounterService {
    public <T> int countOccurrences(BookCollection<T> collection, T target, int threadCount) {
        if (collection == null) {
            throw new IllegalArgumentException("Коллекция не должна быть null");
        }
        InputValidator.requirePositive(threadCount, "Количество потоков");

        int size = collection.size();
        if (size == 0) {
            return 0;
        }

        int workers = Math.min(threadCount, size);
        int chunkSize = (size + workers - 1) / workers;
        ExecutorService executorService = Executors.newFixedThreadPool(workers);

        try {
            List<Future<Integer>> futures = new ArrayList<>();
            for (int start = 0; start < size; start += chunkSize) {
                int end = Math.min(start + chunkSize, size);
                Callable<Integer> task = createCountTask(collection, target, start, end);
                futures.add(executorService.submit(task));
            }

            int totalCount = 0;
            for (Future<Integer> future : futures) {
                totalCount += future.get();
            }
            return totalCount;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Подсчет вхождений был прерван", ex);
        } catch (ExecutionException ex) {
            throw new IllegalStateException("Ошибка во время многопоточного подсчета вхождений", ex);
        } finally {
            executorService.shutdownNow();
        }
    }

    public <T> int countOccurrencesAndPrint(BookCollection<T> collection, T target, int threadCount) {
        return countOccurrencesAndPrint(collection, target, threadCount, ConsoleIO.out());
    }

    public <T> int countOccurrencesAndPrint(
            BookCollection<T> collection,
            T target,
            int threadCount,
            PrintStream out
    ) {
        if (out == null) {
            throw new IllegalArgumentException("Поток вывода не должен быть null");
        }

        int count = countOccurrences(collection, target, threadCount);
        out.println("Количество вхождений элемента \"" + String.valueOf(target) + "\": " + count);
        return count;
    }

    private <T> Callable<Integer> createCountTask(BookCollection<T> collection, T target, int start, int end) {
        return () -> {
            int localCount = 0;
            for (int i = start; i < end; i++) {
                if (Objects.equals(collection.get(i), target)) {
                    localCount++;
                }
            }
            return localCount;
        };
    }
}
