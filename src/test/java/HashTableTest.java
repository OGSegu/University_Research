import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    private final HashTable<String> hashTable = new HashTable<>();
    {
        hashTable.put(new HashTable.HashEntry<>("Vadim", "19"));
        hashTable.put(new HashTable.HashEntry<>("Artem", "13"));
        hashTable.put(new HashTable.HashEntry<>("Vyacheslav", "10"));
        hashTable.put(new HashTable.HashEntry<>("Oleg", "152"));
        hashTable.put(new HashTable.HashEntry<>("Dmitrii", "200"));
    }

    @Test
    void getValue() {
        assertEquals("19", hashTable.getValue("Vadim"));
    }
}