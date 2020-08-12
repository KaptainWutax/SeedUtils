package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.util.SeedIterator;

public final class PillarSeed {

    private static final LCG BACK_2 = LCG.JAVA.combine(-2);

    public static SeedIterator getStructureSeeds(long pillarSeed) {
        return new SeedIterator(0L, 1L << 32, partialStructureSeed -> {
            long currentSeed = (partialStructureSeed & (Mth.MASK_32 - Mth.MASK_16)) << 16;
            currentSeed |= partialStructureSeed & Mth.MASK_16;
            currentSeed |= pillarSeed << 16;
            currentSeed = BACK_2.nextSeed(currentSeed);
            currentSeed ^= LCG.JAVA.multiplier;
            return currentSeed;
        });
    }

    public static SeedIterator iterator() {
        return new SeedIterator(0L, 1L << 16);
    }

}
