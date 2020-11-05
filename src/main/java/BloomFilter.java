public class BloomFilter {

    public static final double error_probability = 0.01;

    private final byte[] byteArray;
    private final int hashFuncAmount;

    BloomFilter(int elementsAmount) {
        if (elementsAmount < 1)
            throw new IllegalArgumentException();
        byteArray = new byte[getOptimalSize(elementsAmount)];
        hashFuncAmount = getHashFuncAmount(elementsAmount);
    }

    public int getOptimalSize(int elementsAmount) {
        return (int) Math.round(-(elementsAmount * Math.log(error_probability)) / (Math.log(2) * Math.log(2)));
    }

    public int getHashFuncAmount(int elementsAmount) {
        return (int) (byteArray.length / elementsAmount * Math.log(2));
    }
}
