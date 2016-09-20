package collection.list;

interface List<T> {

    int size();

    boolean isEmpty();

    void add(T element);

    void add(int i, T element) throws IndexOutOfBoundsException;

    T get(int i) throws IndexOutOfBoundsException;

    // Replace the element at index i with element argument, and returns the replaced element.
    T set(int i, T element) throws IndexOutOfBoundsException;

    T remove(int i) throws  IndexOutOfBoundsException;

}
