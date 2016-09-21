package collection.map;

public interface Map<K, V> {

//    Returns the number of entries in M (map)
    int size();

//    Returns a boolean indicating whether M is empty
    boolean isEmpty();

//    Returns the value v associated with key k,if such an entry exists, otherwise returns null
    V get(K key);

//    If M does not have an entry with key equal to k,
//    then adds entry (k,v) to M and returns null;
//    else, replaces with v the existing value of the entry with key equal to k and returns the old value.
    V put(K key, V value);

//    Removes from M the entry with key equal to k, and returns its value;
//    if M has no such entry, then returns null
    V remove(K key);

//    Returns an iterable collection containing all the keys stored in M
    K[] keySet();

//    Returns an iterable collection containing all the values of entries stored in M
//    (with repetition if multiple keys map to the same value).
    V[] values();

//    Returns an iterable collection containing all the key-value entries in M.
//    entrySet() ???

}
