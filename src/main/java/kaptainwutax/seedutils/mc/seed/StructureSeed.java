package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.seedutils.util.Mth;
import kaptainwutax.seedutils.util.SeedIterator;

import java.util.ArrayList;
import java.util.List;

public final class StructureSeed {

    public static long toPillarSeed(long structureSeed) {
        return (structureSeed >> 16) & Mth.MASK_16;
    }

    public static long toWorldSeed(long structureSeed, long upperBits) {
        return upperBits << 48 | structureSeed;
    }

    //TODO: You can probably do better than brute-force.
    public static List<Long> toRandomWorldSeeds(long structureSeed) {
        List<Long> randomWorldSeeds = new ArrayList<>();

        getWorldSeeds(structureSeed).forEachRemaining(worldSeed -> {
            if(WorldSeed.isRandom(worldSeed)) {
                randomWorldSeeds.add(worldSeed);
            }
        });

        return randomWorldSeeds;
    }

    public static SeedIterator getWorldSeeds(long structureSeed) {
        return new SeedIterator(0L, 1L << 16, upperBits -> toWorldSeed(structureSeed, upperBits));
    }

}
