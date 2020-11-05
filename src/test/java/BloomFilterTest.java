import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    private static final int elementsAmount = 1000;

    BloomFilter bloomFilter = new BloomFilter(elementsAmount);

    private final Logger logger = Logger.getLogger(BloomFilterTest.class.getName());

    @Test
    void getOptimalSize() {
        int optimalSize = bloomFilter.getOptimalSize(elementsAmount);
        logger.log(Level.INFO, "Optimal size per 1k with " + BloomFilter.error_probability + "% for error: " + optimalSize);
        assertEquals(9585, optimalSize);
    }

    @Test
    void getHashFuncAmount() {
        int hashFuncAmount = bloomFilter.getHashFuncAmount(elementsAmount);
        logger.log(Level.INFO, "Optimal hash func amount with " + BloomFilter.error_probability + "% for error: " + hashFuncAmount);
        assertEquals(6, hashFuncAmount);
    }
}