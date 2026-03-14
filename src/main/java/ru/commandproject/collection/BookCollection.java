package ru.commandproject.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public final class BookCollection<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public BookCollection() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T value) {
        ensureCapacity(size + 1);
        elements[size] = value;
        size++;
    }

    public void set(int index, T value) {
        checkIndex(index);
        elements[index] = value;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            Object current = elements[i];
            if (current == null && value == null) {
                return i;
            }
            if (current != null && current.equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> BookCollection<T> fromStream(Stream<T> stream) {
        BookCollection<T> collection = new BookCollection<>();
        stream.forEach(collection::add);
        return collection;
    }

    @Override
    public Iterator<T> iterator() {
        return new BookCollectionIterator();
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity <= elements.length) {
            return;
        }
        int newCapacity = elements.length * 2;
        while (newCapacity < requiredCapacity) {
            newCapacity *= 2;
        }

        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона. Размер коллекции: " + size);
        }
    }

    private final class BookCollectionIterator implements Iterator<T> {
        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Элементы в коллекции закончились");
            }
            return (T) elements[cursor++];
        }
    }
}