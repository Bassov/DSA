package collection.stack;

interface Stack<T> {

    int size();

    boolean isEmpty();

    // It should return the top element of stack but not remove it
    T top();

    void push(T element);

    // It should remove top element and return it
    T pop();

}
