package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;
import ru.commandproject.model.Book;
import ru.commandproject.validation.InputValidator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public final class RandomInputMode implements InputMode<Book> {
    private static final String[] TITLE_PREFIXES = {
            "Тайна", "История", "Путешествие", "Код", "Хроники", "Алгоритм"
    };
    private static final String[] TITLE_SUBJECTS = {
            "океана", "разработчика", "книг", "машин", "времени", "архива"
    };
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1980, 1, 1);
    private static final LocalDate MAX_RELEASE_DATE = LocalDate.of(2025, 12, 31);
    private static final int RELEASE_DATE_RANGE_DAYS =
            (int) (MAX_RELEASE_DATE.toEpochDay() - MIN_RELEASE_DATE.toEpochDay() + 1);

    private final Random random;

    public RandomInputMode() {
        this(new Random());
    }

    public RandomInputMode(Random random) {
        this.random = Objects.requireNonNull(random, "Random не должен быть null");
    }

    @Override
    public BookCollection<Book> read(int count) {
        InputValidator.requirePositive(count, "Количество элементов");

        return BookCollection.fromStream(
                IntStream.range(0, count)
                        .mapToObj(this::generateBook)
        );
    }

    private Book generateBook(int index) {
        return Book.builder()
                .pages(nextPages())
                .title(nextTitle(index))
                .releaseDate(nextReleaseDate())
                .build();
    }

    private int nextPages() {
        return 100 + random.nextInt(901);
    }

    private String nextTitle(int index) {
        String prefix = TITLE_PREFIXES[random.nextInt(TITLE_PREFIXES.length)];
        String subject = TITLE_SUBJECTS[random.nextInt(TITLE_SUBJECTS.length)];
        return prefix + " " + subject + " " + (index + 1);
    }

    private LocalDate nextReleaseDate() {
        int dayOffset = random.nextInt(RELEASE_DATE_RANGE_DAYS);
        return MIN_RELEASE_DATE.plusDays(dayOffset);
    }
}
