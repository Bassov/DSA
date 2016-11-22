package algorithms.optimizationMST;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class MinPriorityQueue<P extends Comparable<P>, K> {

    private static int DEFAULT_SIZE = 100;

    private HashMap<K, Integer> keyMap = new HashMap<>();
    private Entry<P, K>[] heap;
    private int size;

    public MinPriorityQueue() {
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
        keyMap.put(key, i);

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

    public void setPriority(K key, P priority) {
        Integer i = keyMap.get(key);
        if (i == null) {
            return;
        }
        Entry e = heap[i];
        if (e.priority.compareTo(priority) > 0) {
            e.priority = priority;
            upHeap(i);
        } else {
            e.priority = priority;
            downHeap(i);
        }
    }

    public List<K> keys() {
        return keyMap.keySet()
                .stream()
                .collect(Collectors.toList());
    }

    public boolean isIn(K key) {
        return keyMap.containsKey(key);
    }

    public P getPriority(K key) {
        System.out.println(key);
        int i = keyMap.get(key);
        return heap[i].priority;
    }

    public K pop() {
        K toReturn = heap[0].key;
        keyMap.remove(toReturn);
        heap[0] = null;
        size--;

        if (size == 0) {
            return toReturn;
        }

        heap[0] = heap[size];
        heap[size] = null;

        K key = heap[0].key;
        keyMap.replace(key, 0);
        downHeap(0);
        return toReturn;
    }

    private void upHeap(int i) {
        int parentIndex = (i - 1) / 2;
        while (i > 0 && compare(parentIndex, i) > 0) {
            swap(i, parentIndex);
            i = parentIndex;
            parentIndex = (i - 1) / 2;
        }
        K key = heap[i].key;
        keyMap.replace(key, i);
    }

    private void downHeap(int location){
        if (location < size) {
            int p = location;
            int l = 2 * p + 1;
            int r = l + 1;
            int s = size;
            if (r < s && compare(p, r) > 0 && compare(l, r) > 0){
                swap(p, r);
                downHeap(r);
                K key = heap[r].key;
                keyMap.replace(key, r);
            } else if (l < s && compare(p, l) > 0){
                swap(p, l);
                downHeap(l);
                K key = heap[l].key;
                keyMap.replace(key, l);
            }
        }
    }

    private void swap(int i, int j) {
        Entry temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int compare(int i, int j) {
        P priority1 = heap[i].priority;

        P priority2 = heap[j].priority;
        return priority1.compareTo(priority2);
    }

    private void increaseSize() {
        Entry<P, K>[] oldHeap = heap;
        heap = new Entry[oldHeap.length * 2];
        System.arraycopy(oldHeap, 0, heap, 0, oldHeap.length);
    }

    private static class Entry<P extends Comparable, K> {

        private P priority;
        private K key;

        Entry(P priority, K key) {
            this.priority = priority;
            this.key = key;
        }

    }

}
