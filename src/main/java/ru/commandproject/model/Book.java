package ru.commandproject.model;

import ru.commandproject.util.DateParser;
import ru.commandproject.validation.BookValidator;

import java.time.LocalDate;
import java.util.Objects;

public final class Book {
    private final int pages;
    private final String title;
    private final LocalDate releaseDate;

    private Book(Builder builder) {
        this.pages = builder.pages;
        this.title = builder.title;
        this.releaseDate = builder.releaseDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getPages() {
        return pages;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book book)) {
            return false;
        }
        return pages == book.pages
                && Objects.equals(title, book.title)
                && Objects.equals(releaseDate, book.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pages, title, releaseDate);
    }

    @Override
    public String toString() {
        return "Книга{"
                + "страницы=" + pages
                + ", название='" + title + '\''
                + ", дата выхода=" + releaseDate
                + '}';
    }

    public static final class Builder {
        private Integer pages;
        private String title;
        private LocalDate releaseDate;

        private Builder() {
        }

        public Builder pages(int pages) {
            this.pages = pages;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder releaseDate(String releaseDateText) {
            this.releaseDate = DateParser.parse(releaseDateText);
            return this;
        }

        public Book build() {
            BookValidator.validateForBuild(pages, title, releaseDate);
            return new Book(this);
        }
    }
}
