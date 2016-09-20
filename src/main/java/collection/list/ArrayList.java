package collection.list;

import java.util.Iterator;

public class ArrayList<T> implements List<T>, Iterable<T> {

    private static final int MIN_SIZE = 10;

    private int size;
    private T[] container = (T[]) new Object[MIN_SIZE];

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size == 0;
    }

    public final void add(final T element) {
        if (size == container.length) {
            increaseContainer();
        }

        container[size++] = element;
    }

    public final void add(final int i, final T element)
            throws IndexOutOfBoundsException {
        if (i >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == container.length) {
            increaseContainer();
        }

        System.arraycopy(container, i, container, i + 1, size - i);
        container[i] = element;
        size++;
    }

    public final T get(final int i) throws IndexOutOfBoundsException {
        if (i >= this.size()) {
            throw new IndexOutOfBoundsException();
        }

        return container[i];
    }

    public final T set(final int i, final T element)
            throws IndexOutOfBoundsException {
        T replaced = get(i);
        container[i] = element;

        return replaced;
    }

    public final T remove(final int i) throws IndexOutOfBoundsException {
        T removed = get(i);
        System.arraycopy(container, i + 1, container, i, --size - i);

        return removed;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int i = 0;

            public boolean hasNext() {
                return i < size;
            }

            public T next() {
                return get(i++);
            }

        };
    }

    private void increaseContainer() {
        T[] oldContainer = container;
        container = (T[]) new Object[this.size() * 2];
        System.arraycopy(oldContainer, 0, container, 0, oldContainer.length);
    }

}
