package hashtable;

import bst.Bst;
import bst.RedBlackBST;

import java.util.Objects;

public class HashMapNode<Key, Value> implements HashTable<Key, Value> {

    private HashElement[] array;

    private int size;
    private float loadFactor = 0;
    private final float depthThreshold = 3;

    private int primeCounter = 0;
    private final int[] primeNumbers = new int[] {37, 67, 131, 263, 541, 1087, 2179, 4363, 8731, 17467, 34939, 69899, 139801, 279607, 559217, 1118419};

    public HashMapNode() {
        this(16);
    }

    public HashMapNode(int initialCapacity) {
        this.array = new HashElement[initialCapacity];
    }

    @Override
    public Value get(Key key) {
        int index = hash(key);
        if (array[index] == null)
            return null;
        if (array[index] instanceof Node) {
            Node<Key, Value> node = ((Node<Key, Value>) array[index]);
            do {
                if (node.getKey().equals(key))
                    return node.getValue();
            } while ((node = node.getNext()) != null);
            return null;
        } else {
            Bst<Key, Value> bst = (Bst<Key, Value>) array[index];
            return bst.get(key);
        }
    }

    @Override
    public void put(Key key, Value value) {
        if (loadFactor > 0.75F)
            resize();
        putIgnore(key, value);
    }

    private void putIgnore(Key key, Value value) {
        int index = hash(key);
        HashElement hashElement = array[index];
        if (hashElement == null) {
            array[index] = new Node<>(key, value);
            size++;
            return;
        }
        if (hashElement instanceof Bst) {
            Bst<Key, Value> bst = (Bst<Key, Value>) hashElement;
            if (!bst.containsKey(key))
                size++;
            bst.put(key, value);
        } else {
            Node<Key, Value> iterateNode = (Node<Key, Value>) hashElement;
            Node<Key, Value> addNode = new Node<>(key, value);
            int depth = 1;
            while (iterateNode != null) {
                if (iterateNode.getKey().equals(addNode.getKey())) {
                    iterateNode.setValue(addNode.getValue());
                    return;
                }
                if (iterateNode.getNext() == null)
                    break;
                iterateNode = iterateNode.getNext();
                depth++;
            }
            iterateNode.setNext(addNode);
            size++;
            depth++;
            if (depth > depthThreshold) {
                array[index] = nodeToBst((Node<Key, Value>) hashElement);
            }
        }
        loadFactor = size / (float) array.length;
    }

    private Bst<Key, Value> nodeToBst(Node<Key, Value> firstNode) {
        Bst<Key, Value> bst = new RedBlackBST<>();
        Node<Key, Value> node = firstNode;
        do {
            bst.put(node.getKey(), node.getValue());
        } while ((node = node.getNext()) != null);
        return bst;
    }

    @Override
    public Value remove(Key key) {
        int index = hash(key);
        HashElement hashElement = array[index];
        if (hashElement == null)
            return null;
        if (hashElement instanceof Bst) {
            Value removed = ((Bst<Key, Value>) hashElement).remove(key);
            size--;
            return removed;
        } else {
            Node<Key, Value> node = (Node<Key, Value>) hashElement;
            if (node.getKey().equals(key)) {
                Value removed = node.getValue();
                array[index] = null;
                size--;
                return removed;
            } else {
                while (node.getNext() != null) {
                    if (node.getNext().getKey().equals(key)) {
                        Value removed = node.getNext().getValue();
                        node.setNext(null);
                        size--;
                        return removed;
                    }
                    node = node.getNext();
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        size = 0;
        HashElement[] newArray = new HashElement[primeNumbers[primeCounter++]];
        HashElement[] oldArray = array;
        this.array = newArray;
        for (HashElement hashElement : oldArray) {
            if (hashElement == null)
                continue;
            if (hashElement instanceof Bst) {
                RedBlackBST<Key, Value> bst = (RedBlackBST<Key, Value>) hashElement;
                for (RedBlackBST<Key, Value>.BinarySearchTreeIterator bstIterator = bst.iterator();
                     bstIterator.hasNext(); ) {
                    RedBlackBST<Key, Value>.Node current = bstIterator.next();
                    putIgnore(current.getKey(), current.getValue());
                }
            } else {
                Node<Key, Value> node = (Node<Key, Value>) hashElement;
                do {
                    putIgnore(node.getKey(), node.getValue());
                } while ((node = node.getNext()) != null);
            }
        }
    }

    private int hash(Key key) {
        return (Objects.hashCode(key) & 0x7FFFFFFF) % array.length;
    }

}
