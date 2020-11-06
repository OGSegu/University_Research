import java.util.ArrayList;
import java.util.List;

public class HashTable <T> {

    private int maxSize = 100;

    private List<T> arrayList = new ArrayList<>(maxSize);

    {
        for (int i = 0; i < maxSize; i++)
            arrayList.add(null);
    }

    private final HashFunction hashFunction = new HashFunction(2020);


    HashTable() {

    }

    HashTable (int maxSize) {
        this.maxSize = maxSize;
    }

    public void put(HashEntry<T> entry) {
        int index = hashFunction.getHash(entry.key, maxSize);
        arrayList.set(index, entry.value);
    }

    public T getValue(String key) {
        int index = hashFunction.getHash(key, maxSize);
        return arrayList.get(index) != null ? arrayList.get(index) : null;
    }


    public static class HashEntry <T> {

        private final String key;
        private T value;

        HashEntry(String key, T value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }

}
