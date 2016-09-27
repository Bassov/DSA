package collection.map;

import collection.list.ArrayList;

public class HashMap<K, V> extends AbstractMap<K, V> {

    private static final int DEFAULT_SIZE = 17;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] container;
    private Entry<K, V> DEFUNCT = new Entry<>(null, null);
    private int size;

    public HashMap() {
        container = new Entry[DEFAULT_SIZE];
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        for (int i = 0; i < container.length; i++) {
            int index = compress(key, i);

            if (container[index] == null) {
                break;
            }

            if (container[index].getKey() == null) {
                continue;
            }

            if (container[index].getKey().equals(key)) {
                return container[index].getValue();
            }
        }

        return null;
    }

    public V put(K key, V value) {
        for (int i = 0; i < container.length; i++) {
            int index = compress(key, i);

            if (isEmpty(index)) {
                container[index] = new Entry<>(key, value);
                size++;

                if (loadFactor() > LOAD_FACTOR) {
                    increaseContainer();
                }

                return null;
            } else if (container[index].getKey().equals(key)) {
                V toReturn = container[index].getValue();
                container[index].setValue(value);
                return toReturn;
            }
        }

        throw new ArrayIndexOutOfBoundsException();
    }

    public V remove(K key) {
        for (int i = 0; i < container.length; i++) {
            int index = compress(key, i);

            if (container[index] == null) {
                break;
            }

            if (container[index].getKey() == null) {
                continue;
            }

            if (container[index].getKey().equals(key)) {
                V toRemove = container[index].getValue();
                container[index] = DEFUNCT;
                size--;
                return toRemove;
            }
        }

        return null;
    }

    public Iterable<K> keySet() {
        ArrayList<K> keys = new ArrayList<>();

        for (int i = 0; i < container.length; i++) {
            if (!isEmpty(i)) {
                keys.add(container[i].getKey());
            }
        }

        return keys;
    }

    public Iterable<V> values() {
        ArrayList<V> values = new ArrayList<>();

        for (int i = 0; i < container.length; i++) {
            if (!isEmpty(i)) {
                values.add(container[i].getValue());
            }
        }

        return values;
    }

    private boolean isEmpty(int i) {
        return container[i] == null || container[i] == DEFUNCT;
    }

    private double loadFactor() {
        return (double)size / container.length;
    }

    private void increaseContainer() {
        Entry<K, V>[] oldContainer = container;
        container = new Entry[nextPrime(oldContainer.length * 2)];

        for (Entry<K, V> anOldContainer : oldContainer) {
            if (anOldContainer == null || anOldContainer == DEFUNCT) {
                continue;
            }
            put(anOldContainer.getKey(), anOldContainer.getValue());
        }
    }

    private int compress(K key, int i) {
        int hash = key.hashCode();
        int size = container.length;
        int h1 = hash % size;
        int h2 = 1 + (hash % (size - 1));

        return Math.abs(h1 + i * h2) % size;
    }

    private static int nextPrime(int n) {
        do {
            n += 2;
        } while (!isPrime(n));

        return n;
    }

    private static boolean isPrime(int n) {
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

}
