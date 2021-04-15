package kaptainwutax.seedutils.util;

import java.math.BigInteger;
import java.util.function.Predicate;

public class StringUnhasher {

    private static final BigInteger _2 = BigInteger.valueOf(2L);
    private static final BigInteger _31 = BigInteger.valueOf(31L);
    private static final BigInteger[] POW_31 = new BigInteger[64];

    private static final BigInteger MAX_INT = BigInteger.valueOf(0x7FFFFFFFL);
    private static final BigInteger OVERFLOW_CANCEL = MAX_INT.multiply(_2).add(_2);

    static {
        for (int i = 0; i < POW_31.length; i++) {
            POW_31[i] = _31.pow(i);
        }
    }

    public static Config newConfig() {
        return new Config();
    }

    public static void unhash(int hashCode, Config config, Predicate<String> loopPredicate) {
        BigInteger searchHash = BigInteger.valueOf(hashCode);
        BigInteger bigMinChar = BigInteger.valueOf(config.minChar);
        BigInteger bigMaxChar = BigInteger.valueOf(config.maxChar);

        for (int size = config.minSize; size <= config.maxSize; size++) {
            int overflowFrequency = getOverflowFrequency(size, config.maxChar);

            for (int i = 0; i <= overflowFrequency; i++) {
                if (!search(size - 1, searchHash.add(OVERFLOW_CANCEL.multiply(BigInteger.valueOf(i))), 0,
                        BigInteger.ZERO, new char[size], bigMinChar, bigMaxChar, config.filter, loopPredicate)) {
                    return;
                }
            }
        }
    }

    private static boolean search(int maxDepth, BigInteger target, int currentDepth, BigInteger currentHash, char[] currentString,
                                  BigInteger minChar, BigInteger maxChar, Predicate<Character> filter, Predicate<String> loopPredicate) {
        if (currentDepth == maxDepth) {
            BigInteger lastChar = target.subtract(currentHash);
            if (lastChar.compareTo(minChar) < 0 || lastChar.compareTo(maxChar) > 0) return true;
            char c = (char) lastChar.intValue();
            if (!filter.test(c)) return true;

            currentString[currentDepth] = c;
            return loopPredicate.test(new String(currentString));
        }

        int currentExponent = maxDepth - currentDepth;
        BigInteger currentMultiplier = POW_31[currentExponent];

        BigInteger minAddEnd = BigInteger.ZERO;
        BigInteger maxAddEnd = BigInteger.ZERO;

        for (int i = currentExponent - 1; i >= 0; i--) {
            BigInteger multiplier = POW_31[currentExponent];
            minAddEnd = minAddEnd.add(minChar.multiply(multiplier));
            maxAddEnd = maxAddEnd.add(maxChar.multiply(multiplier));
        }

        BigInteger[] res = target.subtract(currentHash).subtract(maxAddEnd).divideAndRemainder(currentMultiplier);
        BigInteger currentMinChar = res[1].signum() == 0 ? res[0] : res[0].add(BigInteger.ONE);
        BigInteger currentMaxChar = target.subtract(currentHash).subtract(minAddEnd).divide(currentMultiplier);

        if (currentMinChar.compareTo(minChar) < 0 || currentMaxChar.compareTo(maxChar) > 0) return true;

        for (BigInteger i = currentMinChar; i.compareTo(currentMaxChar) <= 0; i = i.add(BigInteger.ONE)) {
            char c = (char) i.intValue();
            if (!filter.test(c)) continue;
            currentString[currentDepth] = c;
            if (!search(maxDepth, target, currentDepth + 1,
                    currentHash.add(i.multiply(currentMultiplier)), currentString, minChar, maxChar, filter, loopPredicate)) {
                return false;
            }
        }

        return true;
    }

    private static int getOverflowFrequency(int size, int maxChar) {
        BigInteger u = BigInteger.valueOf(maxChar);

        BigInteger highestHash = BigInteger.ZERO;
        int overflowCount = 0;

        for (int i = 0; i < size; ++i) {
            highestHash = highestHash.multiply(_31).add(u);
        }

        for (; highestHash.compareTo(MAX_INT) > 0; overflowCount++) {
            highestHash = highestHash.subtract(MAX_INT);
        }

        return overflowCount;
    }

    public static class Config {
        private char minChar = ' ';
        private char maxChar = '~';
        private int minSize = 1;
        private int maxSize = 8;
        private Predicate<Character> filter = c -> true;

        private Config() {

        }

        public Config withChars(char minChar, char maxChar) {
            this.minChar = minChar;
            this.maxChar = maxChar;
            return this;
        }

        public Config withSize(int minSize, int maxSize) {
            this.minSize = minSize;
            this.maxSize = maxSize;
            return this;
        }

        public Config filter(Predicate<Character> filter) {
            this.filter = filter;
            return this;
        }
    }

}
