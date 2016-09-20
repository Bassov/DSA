package collection.list;

public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    private int size;

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size == 0;
    }

    public final T first() {
        return head.value;
    }

    public final T last() {
        return tail.value;
    }

    public final void addFirst(final T element) {
        if (head == null) {
            head = new Node<>(element, tail);
            if (tail == null) {
                tail = head;
            }
        } else {
            Node newHead = new Node<>(element, head);
            head = newHead;
        }

        size++;
    }

    public final void addLast(final T element) {
        if (tail == null) {
            tail = new Node<>(element, null);
            if (head == null) {
                head = tail;
            }
        } else {
            Node newTail = new Node<>(element, null);
            tail.nextNode = newTail;
            tail = newTail;
        }

        size++;
    }

    public final T removeFirst() {
        T removed = head.value;

        head = head.nextNode;
        size--;

        return removed;
    }

    private class Node<E> {
        private final E value;
        private Node nextNode;

        Node(final E value, final Node nextNode) {
            this.value = value;
            this.nextNode = nextNode;
        }
    }

}
