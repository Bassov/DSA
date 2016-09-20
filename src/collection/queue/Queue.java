package collection.queue;

interface Queue<T> {

    int size();

    boolean isEmpty();

    T first();

    void enqueue(T element);

    T dequeue();

}
