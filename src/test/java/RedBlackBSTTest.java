import bst.Bst;
import bst.RedBlackBST;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackBSTTest {
    Bst<String, String> newBst() {
        return new RedBlackBST<>();
    }

    @Test
    void emptyBst() {
        Bst<String, String> bst = newBst();
        assertNull(bst.get(""));
        assertNull(bst.get("some key"));
        assertEquals(0, bst.size());
    }

    @Test
    void orderedOnEmpty() {
        Bst<String, String> bst = newBst();
        assertNull(bst.ceil("some key"));
        assertNull(bst.floor("some key"));

        assertNull(bst.min());
        assertNull(bst.max());

        assertNull(bst.minValue());
        assertNull(bst.maxValue());
    }

    @Test
    void put() {
        Bst<String, String> bst = newBst();
        bst.put("foo", "bar");

        assertEquals("bar", bst.get("foo"));

        assertEquals(1, bst.size());
    }

    @Test
    void replace() {
        Bst<String, String> bst = newBst();
        bst.put("foo", "bar");
        bst.put("foo", "bee");

        assertEquals("bee", bst.get("foo"));

        assertEquals(1, bst.size());
    }

    @Test
    void morePut() {
        Bst<String, String> bst = newBst();

        int size = 0;
        assertEquals(bst.size(), size);
        assertNull(bst.max());
        assertNull(bst.min());
        assertNull(bst.get("testStringKey1"));

        bst.put("testStringKey1", "testStringValue1");

        assertEquals(bst.size(), ++size);
        assertEquals("testStringKey1", bst.min());
        assertEquals("testStringKey1", bst.max());
        assertEquals("testStringValue1", bst.get("testStringKey1"));

        bst.put("testStringKey2", "testStringValue2");

        assertEquals(bst.size(), ++size);
        assertEquals("testStringKey1", bst.min());
        assertEquals("testStringKey2", bst.max());
        assertEquals("testStringValue2", bst.maxValue());
        assertEquals("testStringValue2", bst.get("testStringKey2"));

        bst.put("testStringKey2", "case with same value");

        assertEquals(bst.size(), size);
        assertEquals("testStringKey1", bst.min());
        assertEquals("testStringKey2", bst.max());
        assertEquals("case with same value", bst.maxValue());
        assertEquals("case with same value", bst.get("testStringKey2"));

        bst.put("testStringKey3", "testStringValue3");

        assertEquals(bst.size(), ++size);
        assertEquals("testStringKey1", bst.min());
        assertEquals("testStringKey3", bst.max());
        assertEquals("testStringValue3", bst.get("testStringKey3"));

        bst.put("testStringKey", "testStringValue");

        assertEquals(bst.size(), ++size);
        assertEquals("testStringKey", bst.min());
        assertEquals("testStringKey3", bst.max());
        assertEquals("testStringValue", bst.get("testStringKey"));
    }

    @Test
    void remove() {
        Bst<String, String> bst = newBst();
        assertNull(bst.remove("case when bst is empty"));
        assertTrue(bst.isEmpty());

        bst.put("testStringKey3", "testStringValue3");
        bst.put("testStringKey4", "testStringValue4");
        bst.put("testStringKey2", "testStringValue2");
        bst.put("testStringKey5", "testStringValue5");
        bst.put("testStringKey1", "testStringValue1");
        bst.put("testStringKey0", "testStringValue0");

        assertFalse(bst.isEmpty());
        int size = bst.size();

        assertEquals("testStringValue4", bst.remove("testStringKey4"));
        assertEquals(bst.size(), --size);
        assertFalse(bst.containsKey("testStringKey4"));

        assertEquals("testStringValue1", bst.remove("testStringKey1"));
        assertEquals(bst.size(), --size);
        assertFalse(bst.containsKey("testStringKey1"));

        assertNull(bst.remove("testStringKey1"), "testStringValue1");
        assertEquals(bst.size(), size);
        assertFalse(bst.isEmpty());
        assertFalse(bst.containsKey("testStringKey1"));

        assertEquals("testStringValue3", bst.remove("testStringKey3"));
        assertEquals(bst.size(), --size);
        assertFalse(bst.containsKey("testStringKey3"));

        assertEquals("testStringValue0", bst.remove("testStringKey0"));
        assertEquals(bst.size(), --size);
        assertFalse(bst.containsKey("testStringKey0"));

        assertEquals("testStringValue2", bst.remove("testStringKey2"));
        assertEquals(bst.size(), --size);
        assertFalse(bst.containsKey("testStringKey2"));

        assertEquals("testStringValue5", bst.remove("testStringKey5"));
        assertEquals(bst.size(), --size);
        assertFalse(bst.containsKey("testStringKey5"));

        assertTrue(bst.isEmpty());
    }

    @Test
    void max() {
        Bst<String, String> bst = newBst();

        assertNull(bst.max());
        assertNull(bst.maxValue());

        bst.put("testStringKey2", "testStringValue2");

        assertEquals("testStringKey2", bst.max());
        assertEquals("testStringValue2", bst.maxValue());

        bst.put("testStringKey5", "testStringValue5");

        assertEquals("testStringKey5", bst.max());
        assertEquals("testStringValue5", bst.maxValue());

        bst.put("testStringKey0", "testStringValue0");

        assertEquals("testStringKey5", bst.max());
        assertEquals("testStringValue5", bst.maxValue());

        bst.put("testStringKey7", "testStringValue7");

        assertEquals("testStringKey7", bst.max());
        assertEquals("testStringValue7", bst.maxValue());

        bst.remove("testStringKey5");

        assertEquals("testStringKey7", bst.max());
        assertEquals("testStringValue7", bst.maxValue());

        bst.remove("testStringKey7");

        assertEquals("testStringKey2", bst.max());
        assertEquals("testStringValue2", bst.maxValue());

        bst.remove("testStringKey0");

        assertEquals("testStringKey2", bst.max());
        assertEquals("testStringValue2", bst.maxValue());

        bst.remove("testStringKey2");

        assertNull(bst.max());
        assertNull(bst.maxValue());
    }

    @Test
    void min() {
        Bst<String, String> bst = newBst();

        assertNull(bst.min());
        assertNull(bst.minValue());

        bst.put("testStringKey5", "testStringValue5");

        assertEquals("testStringKey5", bst.min());
        assertEquals("testStringValue5", bst.minValue());

        bst.put("testStringKey3", "testStringValue3");

        assertEquals("testStringKey3", bst.min());
        assertEquals("testStringValue3", bst.minValue());

        bst.put("testStringKey9", "testStringValue9");

        assertEquals("testStringKey3", bst.min());
        assertEquals("testStringValue3", bst.minValue());

        bst.put("testStringKey0", "testStringValue0");

        assertEquals("testStringKey0", bst.min());
        assertEquals("testStringValue0", bst.minValue());

        bst.remove("testStringKey5");

        assertEquals("testStringKey0", bst.min());
        assertEquals("testStringValue0", bst.minValue());

        bst.remove("testStringKey0");

        assertEquals("testStringKey3", bst.min());
        assertEquals("testStringValue3", bst.minValue());

        bst.remove("testStringKey9");

        assertEquals("testStringKey3", bst.min());
        assertEquals("testStringValue3", bst.minValue());

        bst.remove("testStringKey3");

        assertNull(bst.min());
        assertNull(bst.minValue());
    }

    @Test
    void contains() {
        Bst<String, String> bst = newBst();

        assertFalse(bst.containsKey("testStringKey"));
        assertFalse(bst.containsKey("testStringKey1"));

        bst.put("testStringKey", "testStringValue");
        assertTrue(bst.containsKey("testStringKey"));
        assertFalse(bst.containsKey("testStringKey1"));

        bst.put("testStringKey1", "testStringValue1");
        assertTrue(bst.containsKey("testStringKey1"));
        assertTrue(bst.containsKey("testStringKey"));

        bst.remove("testStringKey");
        assertTrue(bst.containsKey("testStringKey1"));
        assertFalse(bst.containsKey("testStringKey"));

        bst.remove("testStringKey1");
        assertFalse(bst.containsKey("testStringKey"));
        assertFalse(bst.containsKey("testStringKey1"));
    }

    @Test
    void empty() {
        Bst<String, String> bst = newBst();

        assertTrue(bst.isEmpty());

        bst.put("testStringKey", "testStringValue");
        assertFalse(bst.isEmpty());

        bst.put("testStringKey1", "testStringValue1");
        assertFalse(bst.isEmpty());

        bst.remove("testStringKey");
        assertFalse(bst.isEmpty());

        bst.remove("testStringKey1");
        assertTrue(bst.isEmpty());
    }

    @Test
    void ceil() {
        Bst<String, String> bst = newBst();

        bst.put("1", "testStringValue3");
        bst.put("3", "testStringValue4");
        bst.put("5", "testStringValue2");
        bst.put("7", "testStringValue5");
        bst.put("8", "testStringValue1");
        bst.put("9", "testStringValue0");
        bst.put("2", "testStringValue0");

        assertEquals("1", bst.min());
        assertEquals("9", bst.max());
        assertEquals("5", bst.ceil("5"));
        assertEquals("2", bst.ceil("2"));
        assertEquals("8", bst.ceil("8"));
        assertEquals("1", bst.ceil("0"));
        assertEquals("9", bst.ceil("9"));
        assertNull(bst.ceil("99"));
    }

    @Test
    void floor() {
        Bst<String, String> bst = newBst();

        bst.put("1", "testStringValue3");
        bst.put("3", "testStringValue4");
        bst.put("5", "testStringValue2");
        bst.put("7", "testStringValue5");
        bst.put("8", "testStringValue1");
        bst.put("9", "testStringValue0");
        bst.put("2", "testStringValue0");

        assertEquals("1", bst.min());
        assertEquals("9", bst.max());
        assertEquals("5", bst.floor("5"));
        assertEquals("3", bst.floor("4"));
        assertEquals("8", bst.floor("8"));
        assertEquals("1", bst.floor("1"));
        assertEquals("9", bst.floor("99"));
        assertNull(bst.floor(""));
    }

    @Test
    void moreReplace() {
        Bst<String, String> bst = newBst();

        assertNull(bst.get("1"));

        bst.put("1", "testStringValue3");
        assertEquals("testStringValue3", bst.get("1"));

        bst.put("1", "testStringValue4");
        assertEquals("testStringValue4", bst.get("1"));

        bst.put("1", "testStringValue2");
        assertEquals("testStringValue2", bst.get("1"));

        bst.put("7", "testStringValue5");
        assertEquals("testStringValue5", bst.get("7"));
        assertEquals("testStringValue2", bst.get("1"));
    }
}