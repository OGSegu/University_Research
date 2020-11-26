package bloomfilter;

public class HashFunction {

    private final int seed;

    HashFunction(int seed) {
        this.seed = seed;
    }

    public int getHash(String element, int length) {
        return ((element.hashCode() & 0x7FFFFFFF) / seed) % length;
    }
}