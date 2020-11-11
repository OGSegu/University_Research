import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    private static final int elementsAmount = 5;
    public static final double errorProbability = 0.01;

    BloomFilter bloomFilter = new BloomFilter(elementsAmount, errorProbability);

    private final Logger logger = Logger.getLogger(BloomFilterTest.class.getName());

    @Test
    void getOptimalSize() {
        int optimalSize = bloomFilter.getByteArray().length;
        logger.log(Level.INFO, String.format("Optimal size per %d with %.2f%% for error: %d", elementsAmount, errorProbability * 100, optimalSize));
        assertEquals(48, optimalSize);
    }

    @Test
    void getHashFuncAmount() {
        int hashFuncAmount = bloomFilter.getHashFuncAmount();
        logger.log(Level.INFO, String.format("Optimal hash func amount per %d with %.2f%% for error: %d", elementsAmount, errorProbability * 100, hashFuncAmount));
        assertEquals(6, hashFuncAmount);
    }


    @Test
    void contains() {
        String[] originalArray = {"vadim", "kolya", "ilyas", "artem", "oleg"};
        for (String str : originalArray) {
            bloomFilter.add(str);
        }
        String[] checkArray = {"vadim", "michael", "ilyas", "daniil", "oleg"};
        boolean[] rightAnswers = {true, false, true, false, true};
        boolean result = true;
        for (int i = 0; i < checkArray.length; i++) {
            boolean contains = bloomFilter.contains(checkArray[i]);
            logger.log(Level.INFO, checkArray[i] + " - " + (contains ? "Probably yes" : "NO!"));
            if (rightAnswers[i] != contains)
                result = false;
        }
        assertTrue(result);
    }
}