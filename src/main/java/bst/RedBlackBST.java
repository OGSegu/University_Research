package bst;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * LLRB implementation of binary search tree.
 */
public class RedBlackBST<Key, Value>
        implements Bst<Key, Value> {
    private Node root;

    private static final boolean BLACK = false;
    private static final boolean RED = true;

    int size = 0;

    public class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private boolean color;

        public Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.color = color;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        x.color = y.color;
        y.color = RED;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        y.color = x.color;
        x.color = RED;
        return y;
    }

    private Node flipColors(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
        return x;
    }

    private Node fixUp(Node node) {
        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);
        return node;
    }


    public Value get(Key key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }

    private Node get(Node x, Key key) {
        if (x == null) return null;
        int comparedResult = compareTo(key, x.key);
        if (comparedResult < 0) return get(x.left, key);
        if (comparedResult > 0) return get(x.right, key);
        return x;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            size++;
            return new Node(key, value, RED);
        }
        int comparedResult = compareTo(key, x.key);
        if (comparedResult < 0) {
            x.left = put(x.left, key, value);
        } else if (comparedResult > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
        }
        return fixUp(x);
    }

    public Value remove(Key key) {
        Value removedNode = get(key);
        if (removedNode == null) {
            return null;
        }
        root.color = RED;
        root = remove(root, key);
        size--;
        return removedNode;
    }

    private Node remove(Node x, Key key) {
        if (x == null)
            return null;
        int comparedResult = compareTo(key, x.key);
        if (comparedResult < 0) {
            if (x.left != null) {
                if (!isRed(x.left) && !isRed(x.left.left)) {
                    x = moveRedLeft(x);
                }
                x.left = remove(x.left, key);
            }
        } else {
            if (isRed(x.left)) {
                x = rotateRight(x);
                x.right = remove(x.right, key);
            } else if ((comparedResult == 0) && (x.right == null)) {
                return null;
            } else {
                if (x.right != null && !isRed(x.right) && !isRed(x.right.left)) {
                    x = moveRedRight(x);
                }
                if (x.key == key) {
                    Node minNode = min(x.right);
                    x.key = minNode.key;
                    x.value = minNode.value;
                    x.right = deleteMin(x.right);
                } else {
                    x.right = remove(x.right, key);
                }
            }
        }
        return fixUp(x);
    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return null;
        if (!isRed(x.left) && !isRed(x.left.left))
            x = moveRedLeft(x);
        x.left = deleteMin(x.left);
        return fixUp(x);
    }

    private Node deleteMax(Node x) {
        if (isRed(x.left))
            x = rotateRight(x);
        if (x.right == null)
            return null;
        if (!isRed(x.right) && !isRed(x.right.left))
            x = moveRedRight(x);
        x.right = deleteMax(x.right);
        return fixUp(x);
    }

    private Node moveRedLeft(Node x) {
        flipColors(x);
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    private Node moveRedRight(Node x) {
        flipColors(x);
        if (!isRed(x.left.left))
            x = rotateRight(x);
        return x;
    }


    public Key min() {
        Node resultNode = min(root);
        return resultNode == null ? null : resultNode.key;
    }

    private Node min(Node x) {
        if (x == null) {
            return null;
        }
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Value minValue() {
        Node resultNode = min(root);
        return resultNode == null ? null : resultNode.value;
    }

    public Key max() {
        Node resultNode = max(root);
        return resultNode == null ? null : resultNode.key;
    }

    private Node max(Node x) {
        if (x == null) return null;
        return x.right == null ? x : max(x.right);
    }

    public Value maxValue() {
        Node resultNode = max(root);
        return resultNode == null ? null : resultNode.value;
    }

    public Key floor(Key key) {
        return floor(root, key, null);
    }

    private Key floor(Node node, Key key, Key maxKey) {
        if (node == null) {
            return maxKey;
        }
        if (node.key == key) {
            maxKey = node.key;
        }
        int comparedResult = compareTo(key, node.key);
        if (comparedResult < 0) {
            maxKey = floor(node.left, key, maxKey);
        } else if (comparedResult > 0) {
            maxKey = node.key;
            maxKey = floor(node.right, key, maxKey);
        }
        return maxKey;
    }

    public Key ceil(Key key) {
        Node node = root;
        return ceil(node, key);
    }

    private Key ceil(Node node, Key key) {
        if (node == null) {
            return null;
        }
        if (node.key == key) {
            return node.key;
        }
        int comparedResult = compareTo(key, node.key);
        if (comparedResult < 0) {
            return node.left == null ? node.key : ceil(node.left, key);
        } else if (comparedResult > 0) {
            return node.right == null ? null : ceil(node.right, key);
        }
        return node.key;
    }

    public int size() {
        return size;
    }

    public BinarySearchTreeIterator iterator() {
        return new BinarySearchTreeIterator();
    }

    public class BinarySearchTreeIterator implements Iterator {

        Deque<Node> stack = new ArrayDeque<>();
        Node current = null;

        private BinarySearchTreeIterator() {
            pushLeft(root);
        }

        private void pushLeft(Node node) {
            if (node != null) {
                stack.push(node);
                pushLeft(node.left);
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public Node next() {
            if (stack.isEmpty())
                throw new IllegalStateException();
            Node cur = stack.pop();
            if (cur.right != null)
                pushLeft(cur.right);
            current = cur;
            return cur;
        }
    }
}
