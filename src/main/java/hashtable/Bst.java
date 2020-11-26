package hashtable;

public interface Bst<Key, Value> extends HashElement {

    Value get(Key key);

    default boolean containsKey(Key key) {
        return get(key) != null;
    }

    void put( Key key, Value value);

    Value remove(Key key);

    Key min();

    Value minValue();

    Key max();

    Value maxValue();

    Key floor(Key key);

    Key ceil(Key key);

    default int compareTo(Key key1, Key key2) {
        if ((key1 instanceof Comparable) && (key2 instanceof Comparable)) {
            return ((Comparable) key1).compareTo(key2);
        }
        if (key1.equals(key2))
            return 0;
        else
            return key1.hashCode() > key2.hashCode() ? 1 : -1;
    }
    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

}
