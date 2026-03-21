package ru.commandproject.input;

import ru.commandproject.collection.BookCollection;

public interface InputMode<T> {
    BookCollection<T> read(int count);
}
