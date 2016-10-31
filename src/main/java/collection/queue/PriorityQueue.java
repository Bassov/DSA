package collection.queue;

@SuppressWarnings("unchecked")
public class PriorityQueue<P extends Comparable<P>, K> {

    private static int DEFAULT_SIZE = 16;

    private Entry<P, K>[] heap;
    private int size;

    public PriorityQueue() {
        heap = new Entry[DEFAULT_SIZE];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean put(P priority, K key) {
        if (size == heap.length) {
            increaseSize();
        }

        int i = size;
        heap[size++] = new Entry(priority, key);

        if (i == 0) {
            return true;
        }

        upHeap(i);
        return true;
    }

    public K peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0].key;
    }

    public K pop() {
        K toReturn = heap[0].key;
        size--;

        if (size == 0) {
            return toReturn;
        }

        heap[0] = heap[size];
        heap[size] = null;
        downHeap(0);
        return toReturn;
    }

    private void upHeap(int i) {
        int parentIndex = (i - 1) / 2;
        while (i > 0 && compare(parentIndex, i) < 0) {
            swap(i, parentIndex);
            i = parentIndex;
            parentIndex = (i - 1) / 2;
        }
    }

    private void downHeap(int i) {
        int left = i * 2 + 1;
        int right = left + 1;
        while (compare(i, left) < 0 || compare(i, right) < 0) {
            int max = compare(left, right) > 0 ? left : right;
            swap(i, max);
            i = max;
        }
    }

    private void swap(int i, int j) {
        Entry temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int compare(int i, int j) {
        if (heap[i] == null) return -1;
        P priority1 = heap[i].priority;

        if (heap[j] == null) return 1;
        P priority2 = heap[j].priority;
        return priority1.compareTo(priority2);
    }

    private void increaseSize() {
        Entry<P, K>[] oldHeap = heap;
        heap = new Entry[oldHeap.length * 2];
        System.arraycopy(oldHeap, 0, heap, 0, oldHeap.length);
    }

    private static class Entry<P, K> {

        private P priority;
        private K key;

        Entry(P priority, K key) {
            this.priority = priority;
            this.key = key;
        }

    }

}
