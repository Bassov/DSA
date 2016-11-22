package collection.graph.adj_list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DLinkedList<V> {

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public Node add(V value) {
        Node newNode;
        if (head == null) {
            newNode = new Node(value, null, null);
            head = newNode;
            tail = newNode;
            size++;
            return newNode;
        }

        newNode = new Node(value, tail, null);
        tail.next = newNode;
        tail = newNode;
        size++;

        return newNode;
    }

    public void forEach(Consumer<V> consumer) {
        if (isEmpty()) {
            return;
        }
        Node current = head;
        Node next = current.next;

        while (current != null) {
            consumer.accept(current.value);
            current = next;
            if (current == null) {
                break;
            }
            next = current.next;
        }
    }

    public List<V> values() {
        ArrayList<V> result = new ArrayList<V>();

        if (isEmpty()) {
            return null;
        }
        Node current = head;
        Node next = current.next;

        while (current != null) {
            result.add(current.value);
            current = next;
            if (current == null) {
                break;
            }
            next = current.next;
        }

        return result;
    }

    public boolean anyMatch(Predicate<V> predicate) {
        if (isEmpty()) {
            return false;
        }
        Node current = head;
        Node next = current.next;

        while (current != null) {
            if (predicate.test(current.value)) {
                return true;
            }
            current = next;
            if (current == null) {
                break;
            }
            next = current.next;
        }
        return false;
    }

    public V findBy(Predicate<V> predicate) {
        if (isEmpty()) {
            return null;
        }
        Node current = head;
        Node next = current.next;

        while (current != null) {
            if (predicate.test(current.value)) {
                return current.value;
            }
            current = next;
            if (current == null) {
                break;
            }
            next = current.next;
        }
        return null;
    }

    public class Node {
        V value;
        Node prev, next;

        public Node(V value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public void destroy() {
            if (isEmpty()) {
                return;
            }

            if (prev != null) {
                prev.next = next;
            }

            if (next != null) {
                next.prev = prev;
            } else if (this == head){
                head = null;
                tail = null;
            }
            size--;
        }

    }

}
