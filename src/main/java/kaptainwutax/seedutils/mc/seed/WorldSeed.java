package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.SeedIterator;
import kaptainwutax.seedutils.util.StringUnhasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class WorldSeed {

    /**
     * Converts a world seed to a pillar seed.
     *
     * @see StructureSeed#toPillarSeed(long)
     * */
    public static long toPillarSeed(long worldSeed) {
        return StructureSeed.toPillarSeed(worldSeed);
    }

    /**
     * Checks if a given world seed could be a valid structure seed.
     * */
    public static boolean isStructureSeed(long worldSeed) {
        return WorldSeed.toStructureSeed(worldSeed) == worldSeed;
    }

    /**
     * Converts a world seed to a structure seed by truncating the upper 16 bits.
     * */
    public static long toStructureSeed(long worldSeed) {
        return worldSeed & Mth.MASK_48;
    }

    /**
     * Returns the shadow of a world seed. We define a shadow seed as a seed that has the same
     * biome map but different decorators, structures, terrain, etc. Every world seed has one
     * and only one shadow.
     * */
    public static long getShadowSeed(long worldSeed) {
        return SeedMixer.getOtherSolution(worldSeed);
    }

    /**
     * Provides an iterator to traverse all sister seeds(including itself) of a given world seed.
     *
     * @see StructureSeed#getWorldSeeds(long)
     * */
    public static SeedIterator getSisterSeeds(long worldSeed) {
        return StructureSeed.getWorldSeeds(WorldSeed.toStructureSeed(worldSeed));
    }

    /**
     * Checks if the given world seed can be represented as a string.
     *
     * @see String#hashCode()
     * @see WorldSeed#toString(long, StringUnhasher.Config, Predicate)
     * */
    public static boolean isString(long worldSeed) {
        return (int)worldSeed == worldSeed;
    }

    /**
     * Converts a world seed to string equivalents matching the config requirements.
     * A predicate is provided to stop the search early if the search target was found.
     *
     * @see String#hashCode()
     * @see WorldSeed#isString(long)
     * */
    public static void toString(long worldSeed, StringUnhasher.Config config, Predicate<String> continueSearching) {
        if(isString(worldSeed)) {
            StringUnhasher.unhash((int)worldSeed, config, continueSearching);
        }
    }

    /**
     * Checks if the given world seed can be generated randomly via {@code nextLong()}.
     * I know it looks like gibberish... but it's very speedy.
     *
     * Take that! https://twitter.com/Geosquare_/status/1169623192153010176
     *
     * @see JRand#nextLong()
     * */
    public static boolean isRandom(long worldSeed) {
        long upperBits = worldSeed >>> 32;
        long lowerBits = worldSeed & Mth.MASK_32;

        long a = (24667315L * upperBits + 18218081L * lowerBits + 67552711L) >> 32;
        long b = (-4824621L * upperBits + 7847617L * lowerBits + 7847617L) >> 32;
        long seed = 7847617L * a - 18218081L * b;

        return JRand.nextLong(seed) == worldSeed;
    }

    /**
     * Finds the correct world seed given a structure seed and the sha256 representation
     * of the world seed. This is interesting for seed cracking because since 1.15, the
     * server sends the hashed seed to the client to handle the last layer of biome generation.
     *
     * This method returns a list since there's no way to prove collisions don't exist. The
     * chances are VERY low but that's what perfectionism does.
     *
     * @see WorldSeed#toHash(long)
     * */
    public static List<Long> fromHash(long structureSeed, long hashedWorldSeed) {
        List<Long> worldSeeds = new ArrayList<>();

        StructureSeed.getWorldSeeds(structureSeed).forEachRemaining(worldSeed -> {
            if(WorldSeed.toHash(worldSeed) == hashedWorldSeed) {
                worldSeeds.add(worldSeed);
            }
        });

        if(worldSeeds.size() > 1) {
            System.err.format("This should never happen. Open a github issue right now and provide the structure " +
                    "seed [%d] as well as the hashed seed [%d], you have stumbled upon an incredibly rare collision.\n", structureSeed, hashedWorldSeed);
        }

        return worldSeeds;
    }

    /**
     * Converts the given world seed to it's truncated sha256 value. This is equivalent
     * to the hashed seed the server sends to the client since 1.15.
     *
     * @see WorldSeed#fromHash(long, long)
     * */
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

    /**
     * Provides an iterator to go through all possible world seeds which can
     * be generated randomly through a {@code nextLong()} call.
     *
     * @see WorldSeed#isRandom(long)
     * */
    public static SeedIterator randomSeedsIterator() {
        return new SeedIterator(0L, 1L << 48, JRand::nextLong);
    }

}
