package hashtable;

public class Node<Key, Value> implements HashElement {
    private final Key key;
    private Value value;
    private Node next;

    public Node(Key key) {
        this(key, null, null);
    }

    public Node(Key key, Value value) {
        this(key, value, null);
    }

    public Node(Key key, Value value, Node<Key, Value> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    public Node<Key, Value> getNext() {
        return next;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void setNext(Node<Key, Value> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                ", next=" + next +
                '}';
    }
}
