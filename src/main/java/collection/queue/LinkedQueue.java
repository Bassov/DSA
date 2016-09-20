package collection.queue;

import collection.list.LinkedList;

public class LinkedQueue<T> implements Queue<T> {

    private int size;
    private LinkedList<T> container = new LinkedList<>();

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size == 0;
    }

    public final T first() {
        return container.first();
    }

    public final void enqueue(final T element) {
        container.addLast(element);
        size++;
    }

    public final T dequeue() {
        T removed = container.removeFirst();
        size--;
        return removed;
    }

}
