package collection.tree;

public class BinarySearchTree<T extends Comparable> {

    public int size;
    public Node root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void add(T element) {
        Node toSet = new Node(null, null, element);

        if (isEmpty()) {
            root = toSet;
            size++;
            return;
        }

        Node current = root;
        boolean notSet = true;

        while (notSet) {
            if (element.compareTo(current.value) == 0) {
                return;
            }

            if (element.compareTo(current.value) > 0) {

                if (current.right == null) {
                    current.right = toSet;
                    notSet = false;
                } else {
                    current = current.right;
                }

            } else {

                if (current.left == null) {
                    current.left = toSet;
                    notSet = false;
                } else {
                    current = current.left;
                }

            }
        }
    }



    public class Node<T> {

        Node left;
        Node right;
        T value;

        Node(Node left, Node right, T value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public T getValue() {
            return value;
        }

    }

}
