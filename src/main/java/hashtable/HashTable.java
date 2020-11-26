package hashtable;

public interface HashTable<Key, Value> {
    Value get(Key key);

    default boolean containsKey(Key key) {
        return get(key) != null;
    }

    void put(Key key, Value value);

    Value remove(Key key);

    int size();

    boolean isEmpty();
}