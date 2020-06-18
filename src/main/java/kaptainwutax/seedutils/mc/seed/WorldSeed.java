package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.util.SeedIterator;
import kaptainwutax.seedutils.util.StringUnhasher;
import kaptainwutax.seedutils.util.math.Mth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class WorldSeed {

    public static long toPillarSeed(long worldSeed) {
        return (worldSeed >>> 16) & Mth.MASK_16;
    }

    public static long toStructureSeed(long worldSeed) {
        return worldSeed & Mth.MASK_48;
    }

    public static long getShadowSeed(long worldSeed) {
        return SeedMixer.getOtherSolution(worldSeed);
    }

    public static SeedIterator getSisterSeeds(long worldSeed) {
        return StructureSeed.getWorldSeeds(toStructureSeed(worldSeed));
    }

    public static boolean isString(long worldSeed) {
        return (int)worldSeed == worldSeed;
    }

    public static void toString(long worldSeed, StringUnhasher.Config config, Predicate<String> continueSearching) {
        if(isString(worldSeed)) {
            StringUnhasher.unhash((int)worldSeed, config, continueSearching);
        }
    }

    public static boolean isRandom(long worldSeed) {
        long upperBits = worldSeed >>> 32;
        long lowerBits = worldSeed & Mth.MASK_32;

        long a = (24667315L * upperBits + 18218081L * lowerBits + 67552711L) >> 32;
        long b = (-4824621L * upperBits + 7847617L * lowerBits + 7847617L) >> 32;
        long seed = 7847617L * a - 18218081L * b;

        //Compute the nextLong() call fast without creating a JRand object.
        long nextLong = (seed >>> 16 << 32) + (int)(LCG.JAVA.nextSeed(seed) >>> 16);
        return nextLong == worldSeed;
    }

    public static List<Long> fromHash(long structureSeed, long hashedWorldSeed) {
        List<Long> worldSeeds = new ArrayList<>();

        StructureSeed.getWorldSeeds(structureSeed).forEachRemaining(worldSeed -> {
            if(WorldSeed.toHash(worldSeed) == hashedWorldSeed) {
                worldSeeds.add(worldSeed);
            }
        });

        return worldSeeds;
    }

    public static long toHash(long worldSeed) {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return worldSeed;
        }

        byte[] bytes = new byte[8];

        for(int i = 0; i < 8; i++) {
            bytes[i] = (byte)(worldSeed & 0xFFL);
            worldSeed >>>= 8;
        }

        bytes = digest.digest(bytes);
        long hashedWorldSeed = bytes[0] & 0xFFL;

        for(int i = 1; i < 8; i++) {
            hashedWorldSeed |= (bytes[i] & 0xFFL) << (i << 3);
        }

        return hashedWorldSeed;
    }

    public SeedIterator randomSeedsIterator() {
        return new SeedIterator(0L, 1L << 48, seed -> {
            //Compute the nextLong() call fast without creating a JRand object.
            return (seed >>> 16 << 32) + (int)(LCG.JAVA.nextSeed(seed) >>> 16);
        });
    }

}
