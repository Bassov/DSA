package collection.tree;

import java.util.function.BiConsumer;

public class AVLTree<K extends Comparable<K>, V> {

    private Node root;

    public void put(K key, V value) {
        Node node = new Node(key, value);
        put(root, node);
    }

    public void remove(K key) {
        remove(root, key);
    }

    public V get(K key) {
        Node toReturn = get(root, key);
        if (toReturn == null) {
            return null;
        }
        return toReturn.value;
    }

    public void traverse(BiConsumer visitor) {
        Node start = min(root);
        while (start != null) {
            visitor.accept(start.key, start.value);
            start = successor(start);
        }
    }

    public void traverse(BiConsumer visitor, K start, K end) {
        Node current = findStart(root, start, null);
        if (current == null) {
            return;
        }

        int comp = current.key.compareTo(end);
        while (comp <= 0) {
            visitor.accept(current.key, current.value);
            current = successor(current);
            if (current == null) break;
            comp = current.key.compareTo(end);
        }
    }

    private Node get(Node start, K key) {
        if (start == null) {
            return null;
        }

        int comp = key.compareTo(start.key);
        if (comp < 0) {
            return get(start.left, key);
        } else if (comp > 0) {
            return get(start.right, key);
        } else {
            return start;
        }
    }

    private Node findStart(Node start, K key, Node acc) {
        int comp = key.compareTo(start.key);
        if (comp < 0) {

            if (start.left == null) {
                return acc;
            } else {
                return findStart(start.left, key, start);
            }

        } else if (comp > 0) {

            if (start.right == null) {
                return acc;
            } else {
                return findStart(start.right, key, acc);
            }

        } else {
            return start;
        }
    }

    private void put(Node node, Node toInsert) {
        if (node == null) {
            root = toInsert;
        } else {

            int cmp = node.key.compareTo(toInsert.key);
            if (cmp > 0) {
                if (node.left == null) {
                    node.left = toInsert;
                    toInsert.parent = node;
                    balance(node);
                } else {
                    put(node.left, toInsert);
                }
            } else if (cmp < 0) {
                if (node.right == null) {
                    node.right = toInsert;
                    toInsert.parent = node;
                    balance(node);
                } else {
                    put(node.right, toInsert);
                }
            } else {
                node.value = toInsert.value;
            }
        }
    }

    private void balance(Node start) {
        setBalance(start);
        int balance = start.balance;
        if (balance == -2) {
            if (height(start.left.left) >= height(start.left.right)) {
                start = rotateRight(start);
            } else {
                start = rotateLeftRight(start);
            }
        } else if (balance == 2) {
            if (height(start.right.right) >= height(start.right.left)) {
                start = rotateLeft(start);
            } else {
                start = rotateRightLeft(start);
            }
        }

        if (start.parent != null) {
            balance(start.parent);
        } else {
            this.root = start;
        }
    }

    private void remove(Node parent, K key) {
        if(parent == null) {
            return;
        } else {
            int comp = parent.key.compareTo(key);
            if (comp > 0)  {
                remove(parent.left, key);
            } else if (comp < 0) {
                remove(parent.right, key);
            } else {
                remove(parent);
            }
        }
    }

    private void remove(Node node) {
        Node toRemove;
        if (node.left == null || node.right == null) {
            if (node.parent == null) {
                root = null;
                node = null;
                return;
            }

            toRemove = node;
        } else {
            toRemove = successor(node);
            node.key = toRemove.key;
        }

        Node p;
        if (toRemove.left!=null) {
            p = toRemove.left;
        } else {
            p = toRemove.right;
        }

        if (p != null) {
            p.parent = toRemove.parent;
        }

        if (toRemove.parent==null) {
            this.root = p;
        } else {
            if(toRemove == toRemove.parent.left) {
                toRemove.parent.left = p;
            } else {
                toRemove.parent.right = p;
            }
            balance(toRemove.parent);
        }
        toRemove = null;
    }

    private Node rotateLeft(Node node) {
        Node v = node.right;
        v.parent = node.parent;

        node.right = v.left;

        if (node.right != null) {
            node.right.parent = node;
        }

        v.left = node;
        node.parent = v;

        if (v.parent != null) {
            if (v.parent.right == node) {
                v.parent.right = v;
            } else if (v.parent.left == node) {
                v.parent.left = v;
            }
        }

        setBalance(node);
        setBalance(v);

        return v;
    }

    private Node rotateRight(Node n) {
        Node v = n.left;
        v.parent = n.parent;

        n.left = v.right;

        if (n.left != null) {
            n.left.parent = n;
        }

        v.right = n;
        n.parent = v;


        if (v.parent != null) {
            if (v.parent.right == n) {
                v.parent.right = v;
            } else if (v.parent.left == n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    private Node rotateLeftRight(Node node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    private Node rotateRightLeft(Node node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    private Node successor(Node node) {
        if (node.right != null) {
            Node r = node.right;
            while (r.left != null) {
                r = r.left;
            }
            return r;
        } else {
            Node parent = node.parent;
            while (parent != null && node == parent.right) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }

        if (node.left == null && node.right == null) {
            return 0;
        } else if (node.left == null) {
            return 1 + height(node.right);
        } else if (node.right == null) {
            return 1 + height(node.left);
        } else {
            return 1 + Math.max(height(node.left), height(node.right));
        }
    }

    private void setBalance(Node cur) {
        cur.balance = height(cur.right) - height(cur.left);
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private class Node {

        K key;
        V value;

        int balance;

        Node left;
        Node right;
        Node parent;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

}
