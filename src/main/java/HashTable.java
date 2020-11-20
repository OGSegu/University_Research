import java.util.Objects;

public class HashTable {

    private String[] array;

    private int size;
    private float loadFactor = 0;

    public HashTable() {
        this(16);
    }

    public HashTable(int initialCapacity) {
        this.array = new String[initialCapacity];
    }


    public String get(String key) {
        int index = hash(key);
        return array[index];
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }


    public void put(String key, String value) {
        if (loadFactor > 0.75F)
            resize();
        int index = hash(key);
        if (array[index] == null)
            size++;
        array[index] = value;
        loadFactor = size / (float) array.length;
    }


    public String remove(String key) {
        String removedElement = get(key);
        if (removedElement == null)
            return null;
        int index = hash(key);
        array[index] = null;
        size--;
        return removedElement;
    }


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        String[] newArray = new String[size * 2];
        String[] oldArray = array.clone();
        this.array = newArray;
        for (String element : oldArray) {
            if (element == null)
                continue;
            int newIndex = hash(element);
            array[newIndex] = element;
        }
    }

    private int hash(String key) {
        return Objects.hashCode(key) % array.length;
    }

}