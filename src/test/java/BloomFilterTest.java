import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    private static final int elementsAmount = 5;
    public static final double error_probability = 0.01;

    BloomFilter bloomFilter = new BloomFilter(elementsAmount, error_probability);

    private final Logger logger = Logger.getLogger(BloomFilterTest.class.getName());

    @Test
    void getOptimalSize() {
        int optimalSize = bloomFilter.getOptimalSize(elementsAmount);
        logger.log(Level.INFO, "Optimal size per 1k with " + error_probability + "% for error: " + optimalSize);
        assertEquals(9585, optimalSize);
    }

    @Test
    void getHashFuncAmount() {
        int hashFuncAmount = bloomFilter.getHashFuncAmount(elementsAmount);
        logger.log(Level.INFO, "Optimal hash func amount with " + error_probability + "% for error: " + hashFuncAmount);
        assertEquals(6, hashFuncAmount);
    }

    @Test
    void add() {
        String[] array = {"vadim", "misha", "dasha", "regina", "ilya"};
        for (String str : array) {
            bloomFilter.add(str);
        }
    }

    @Test
    void contains() {
        String[] array = {"vadim", "kolya", "ilyas", "artem", "oleg"};
        for (String str : array) {
            logger.log(Level.INFO, bloomFilter.contains(str) ?  str + ": probably yes" : str + ": no");
        }
    }
}