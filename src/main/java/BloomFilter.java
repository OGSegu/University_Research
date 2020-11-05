import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BloomFilter {

    private final byte[] byteArray;
    private final List<HashFunction> hashesList = new ArrayList<>();

    private final int hashFuncAmount;
    public final double error_probability;

    BloomFilter(int elementsAmount, double errorProbability) {
        if (elementsAmount < 1)
            throw new IllegalArgumentException();
        this.error_probability = errorProbability;
        this.byteArray = new byte[getOptimalSize(elementsAmount)];
        this.hashFuncAmount = getHashFuncAmount(elementsAmount);
        generateHashes();
    }

    public int getOptimalSize(int elementsAmount) {
        return (int) Math.round(-(elementsAmount * Math.log(error_probability)) / (Math.log(2) * Math.log(2)));
    }

    public int getHashFuncAmount(int elementsAmount) {
        return (int) (byteArray.length / elementsAmount * Math.log(2));
    }

    private void generateHashes() {
        Random random = new Random();
        for (int i = 0; i < hashFuncAmount; i++)
            hashesList.add(new HashFunction(random.nextInt(50) + 1));
        }

    public void add(String element) {
        for (HashFunction hashFunction : hashesList) {
            int index = hashFunction.getHash(element);
            byteArray[index] = 1;
        }
    }

    public boolean contains(String element) {
        boolean result = true;
        for (HashFunction hashFunction : hashesList) {
            int index = hashFunction.getHash(element);
            result &= byteArray[index] == 1;
        }
        return result;
    }

    public class HashFunction {

        private final int seed;

        HashFunction(int seed) {
            this.seed = seed;
        }

        public int getHash(String element) {
            int hash = (Math.abs(element.hashCode()) / seed) % byteArray.length;
            return hash;
        }
    }

}
