package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;

/**
 * Helper class for quickly getting chunk seeds without creating a {@code ChunkRand} instance.
 * Note that calls to {@code ChunkSeeds} are NOT thread-safe.
 */
public final class ChunkSeeds {

    private static final ChunkRand INTERNAL = new ChunkRand();

    /**
     * @see ChunkRand#setTerrainSeed(int, int, MCVersion)
     */
    public static long getTerrainSeed(int chunkX, int chunkZ, MCVersion version) {
        return INTERNAL.setTerrainSeed(chunkX, chunkZ, version);
    }

    /**
     * @see ChunkRand#setPopulationSeed(long, int, int, MCVersion)
     */
    public static long getPopulationSeed(long worldSeed, int x, int z, MCVersion version) {
        return INTERNAL.setPopulationSeed(worldSeed, x, z, version);
    }

    /**
     * @see ChunkRand#setDecoratorSeed(long, int, int, MCVersion)
     */
    public static long getDecoratorSeed(long populationSeed, int index, int step, MCVersion version) {
        return INTERNAL.setDecoratorSeed(populationSeed, index, step, version);
    }

    /**
     * @see ChunkRand#setDecoratorSeed(long, int, int, int, int, MCVersion)
     */
    public static long getDecoratorSeed(long worldSeed, int blockX, int blockZ, int index, int step, MCVersion version) {
        return INTERNAL.setDecoratorSeed(worldSeed, blockX, blockZ, index, step, version);
    }

    /**
     * @see ChunkRand#setCarverSeed(long, int, int, MCVersion)
     */
    public static long getCarverSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
        return INTERNAL.setCarverSeed(worldSeed, chunkX, chunkZ, version);
    }

    /**
     * @see ChunkRand#setRegionSeed(long, int, int, int, MCVersion)
     */
    public static long getRegionSeed(long worldSeed, int regionX, int regionZ, int salt, MCVersion version) {
        return INTERNAL.setRegionSeed(worldSeed, regionX, regionZ, salt, version);
    }

    /**
     * @see ChunkRand#setWeakSeed(long, int, int, MCVersion)
     */
    public static long getWeakSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
        return INTERNAL.setWeakSeed(worldSeed, chunkX, chunkZ, version);
    }

    /**
     * @see ChunkRand#setSlimeSeed(long, int, int, MCVersion)
     */
    public static long getSlimeSeed(long worldSeed, int chunkX, int chunkZ, long scrambler, MCVersion version) {
        return INTERNAL.setSlimeSeed(worldSeed, chunkX, chunkZ, scrambler, version);
    }

    /**
     * @see ChunkRand#setSlimeSeed(long, int, int, long, MCVersion)
     */
    public static long getSlimeSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
        return INTERNAL.setSlimeSeed(worldSeed, chunkX, chunkZ, version);
    }

}
