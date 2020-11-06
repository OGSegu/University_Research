public class HashFunction {

    private final int seed;

    HashFunction(int seed) {
        this.seed = seed;
    }

    public int getHash(String element, int length) {
        return (element.hashCode() & 0xfffffff / seed) % length;
    }
}