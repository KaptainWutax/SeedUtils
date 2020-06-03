package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.seedutils.util.SeedIterator;
import kaptainwutax.seedutils.util.math.Mth;

import java.util.ArrayList;
import java.util.List;

public final class StructureSeed {

    public static long toPillarSeed(long structureSeed) {
        return (structureSeed >>> 16) & Mth.MASK_16;
    }

    /**
     * Appends the upper bits to the structure seed to create a world seed.
     * */
    public static long toWorldSeed(long structureSeed, long upperBits) {
        return upperBits << 48 | structureSeed;
    }

    /**
     * Returns the possible values the upper bits can take assuming the world seed
     * was generated from a {@code nextLong()} call or, in other words, generated
     * randomly after leaving the seed field empty.
     * */
    public static List<Long> toRandomWorldSeeds(long structureSeed) {
        List<Long> randomWorldSeeds = new ArrayList<>();

        //TODO: You can do better than brute-force. Smh...
        getWorldSeeds(structureSeed).forEachRemaining(worldSeed -> {
            if(WorldSeed.isRandom(worldSeed)) {
                randomWorldSeeds.add(worldSeed);
            }
        });

        return randomWorldSeeds;
    }

    /**
     * Returns an iterator generating all possible world seeds from a given structure
     * seed by going through all possible upper 16 bits.
     * */
    public static SeedIterator getWorldSeeds(long structureSeed) {
        return new SeedIterator(0L, 1L << 16, upperBits -> toWorldSeed(structureSeed, upperBits));
    }

    /**
     * Returns an iterator generating all possible structure seeds. Note that you should
     * not expect this to terminate in a reasonable amount of times since the amount of values
     * it can take is 2^48 (around 281 trillion).
     * */
    public static SeedIterator iterator() {
        return new SeedIterator(0L, 1L << 48);
    }

}
