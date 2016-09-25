package collection.map;

public abstract class AbstractMap<K, V> implements Map<K, V> {

    public boolean isEmpty() {
        return size() == 0;
    }

    protected class Entry<K, V> {

        private K key;
        private V value;

        protected Entry(){

        }

        protected Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        protected K getKey() {
            return key;
        }

        protected void setKey(K key) {
            this.key = key;
        }

        protected V getValue() {
            return value;
        }

        protected V setValue(V value) {
            V old = value;
            this.value = value;
            return old;
        }

    }

}
