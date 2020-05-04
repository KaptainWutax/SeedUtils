package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.util.Mth;
import kaptainwutax.seedutils.util.SeedIterator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public final class WorldSeed {

    public static long toPillarSeed(long worldSeed) {
        return StructureSeed.toPillarSeed(toStructureSeed(worldSeed));
    }

    public static long toStructureSeed(long worldSeed) {
        return worldSeed & Mth.MASK_48;
    }

    public static SeedIterator getSisterSeeds(long worldSeed) {
        return StructureSeed.getWorldSeeds(toStructureSeed(worldSeed));
    }

    public static boolean isRandom(long worldSeed) {
        long upperBits = worldSeed >>> 32;
        long lowerBits = worldSeed & Mth.MASK_32;

        long a = (24667315L * upperBits + 18218081L * lowerBits + 67552711L) >> 32;
        long b = (-4824621L * upperBits + 7847617L * lowerBits + 7847617L) >> 32;
        long seed = 7847617L * a - 18218081L * b;
        long nextLong = ((long)(int)(seed >>> 16) << 32) + (int)(LCG.JAVA.nextSeed(seed) >>> 16);
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
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return worldSeed;
        }

        ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(worldSeed);
        digest.update(buffer.array());

        byte[] bytes = digest.digest();
        long hashedWorldSeed = bytes[0] & 0xFF;

        for(int i = 1; i < 8; i++) {
            hashedWorldSeed |= (bytes[i] & 0xFFL) << (i * 8);
        }

        return hashedWorldSeed;
    }

}
