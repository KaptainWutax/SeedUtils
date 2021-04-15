package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.SeedIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PillarSeed {

    private static final LCG SKIP_2 = LCG.JAVA.combine(2);
    private static final LCG BACK_2 = LCG.JAVA.combine(-2);

    /**
     * Gets the pillar seed of a given structure seed. This method is equivalent
     * to calling {@code new JRand(structureSeed).nextLong() & 65535}.
     */
    public static long fromStructureSeed(long structureSeed) {
        return (SKIP_2.nextSeed(structureSeed ^ LCG.JAVA.multiplier) >>> 16) & Mth.MASK_16;
    }

    /**
     * Provides an iterator that gives all structure seeds that have the given
     * pillar seed. This is an important tool for seed cracking since it reduces the
     * search space from 2^48 to 2^32.
     */
    public static SeedIterator getStructureSeeds(long pillarSeed) {
        return new SeedIterator(0L, 1L << 32, partialStructureSeed -> {
            long currentSeed = (partialStructureSeed & (Mth.MASK_32 - Mth.MASK_16)) << 16;
            currentSeed |= partialStructureSeed & Mth.MASK_16;
            currentSeed |= pillarSeed << 16;
            currentSeed = BACK_2.nextSeed(currentSeed);
            return currentSeed ^ LCG.JAVA.multiplier;
        });
    }

    /**
     * Returns the pillar heights given a pillar seed. The height is represented
     * by the y coordinate of the bedrock block on top of the pillars.
     *
     * @see PillarSeed#fromPillarHeights(int[])
     */
    public static int[] getPillarHeights(long pillarSeed) {
        int[] heights = new int[10];
        for (int i = 0; i < 10; i++) heights[i] = 76 + i * 3;

        JRand rand = new JRand(pillarSeed);

        for (int i = heights.length; i > 1; i--) {
            int a = i - 1, b = rand.nextInt(i);
            int temp = heights[a];
            heights[a] = heights[b];
            heights[b] = temp;
        }

        return heights;
    }

    /**
     * Find the pillar seed(s) from a list of heights. Useful for seed cracking.
     *
     * @see PillarSeed#getPillarHeights(long)
     */
    public static List<Long> fromPillarHeights(int[] heights) {
        List<Long> seeds = new ArrayList<>();

        PillarSeed.iterator().forEachRemaining(pillarSeed -> {
            if (Arrays.equals(getPillarHeights(pillarSeed), heights)) {
                seeds.add(pillarSeed);
            }
        });

        return seeds;
    }

    /**
     * Provides an iterator to go through all possible pillar seeds.
     */
    public static SeedIterator iterator() {
        return new SeedIterator(0L, 1L << 16);
    }

}
