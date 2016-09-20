package collection.stack;

import collection.list.LinkedList;

public class LinkedStack<T> implements Stack<T> {

    private int size;
    private LinkedList<T> container = new LinkedList<>();

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size == 0;
    }

    public final T top() {
        return container.first();
    }

    public final void push(final T element) {
        container.addFirst(element);
        size++;
    }

    public final T pop() {
        if (size > 0) {
            size--;
        }
        return container.removeFirst();
    }

}
