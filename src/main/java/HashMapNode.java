import java.util.Objects;

public class HashMapNode<Key extends Comparable<Key>, Value> {

    private RedBlackBST<Key, Value>[] array;

    private int size;
    private float loadFactor = 0;

    public HashMapNode() {
        this(16);
    }

    public HashMapNode(int initialCapacity) {
        this.array = new RedBlackBST[initialCapacity];
    }

    public Value get(Key key) {
        int index = hash(key);
        RedBlackBST<Key, Value> bst = array[index];
        return bst == null ? null : bst.get(key);
    }

    boolean containsKey(Key key) {
        return get(key) != null;
    }

    public void put(Key key, Value value) {
        if (loadFactor > 0.3F)
            resize();
        putIgnore(key, value);
    }

    private void putIgnore(Key key, Value value) {
        int index = hash(key);
        RedBlackBST<Key, Value> bst = array[index];
        if (bst == null)
            bst = new RedBlackBST<>();
        if (!bst.containsKey(key))
            size++;
        bst.put(key, value);
        array[index] = bst;
        loadFactor = size / (float) array.length;
    }

    public Value remove(Key key) {
        int index = hash(key);
        RedBlackBST<Key, Value> bst = array[index];
        if (bst == null)
            return null;
        Value removed = bst.remove(key);
        if (removed == null)
            return null;
        size--;
        return removed;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        size = 0;
        RedBlackBST<Key, Value>[] newArray = new RedBlackBST[array.length * 2];
        RedBlackBST<Key, Value>[] oldArray = array;
        this.array = newArray;
        for (RedBlackBST<Key, Value> bst: oldArray) {
            if (bst == null)
                continue;
            for(RedBlackBST<Key, Value>.BinarySearchTreeIterator bstIterator = bst.iterator();
                bstIterator.hasNext();) {
                RedBlackBST<Key, Value>.Node current = bstIterator.next();
                putIgnore(current.getKey(), current.getValue());
            }
        }
    }

    private int hash(Key key) {
        return (Objects.hashCode(key) & 0x7FFFFFFF) % array.length;
    }

}