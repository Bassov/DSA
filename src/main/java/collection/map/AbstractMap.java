package collection.map;

public abstract class AbstractMap<K, V> implements Map<K, V> {

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    protected class Entry<K, V> {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V old = value;
            this.value = value;
            return old;
        }

    }

}
