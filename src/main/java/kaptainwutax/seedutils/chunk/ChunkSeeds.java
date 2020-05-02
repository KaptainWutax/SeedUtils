package kaptainwutax.seedutils.chunk;

import kaptainwutax.seedutils.util.MCVersion;

public final class ChunkSeeds {

	private static final ChunkRand INTERNAL = new ChunkRand(0L, false);

	public static long getTerrainSeed(int chunkX, int chunkZ, MCVersion version) {
		return INTERNAL.setTerrainSeed(chunkX, chunkZ, version);
	}

	public static long getPopulationSeed(long worldSeed, int x, int z, MCVersion version) {
		return INTERNAL.setPopulationSeed(worldSeed, x, z, version);
	}

	public static long getDecoratorSeed(long populationSeed, int index, int step, MCVersion version) {
		return INTERNAL.setDecoratorSeed(populationSeed, index, step, version);
	}

	public static long getDecoratorSeed(long worldSeed, int blockX, int blockZ, int index, int step, MCVersion version) {
		return INTERNAL.setDecoratorSeed(worldSeed, blockX, blockZ, index, step, version);
	}

	public static long getCarverSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		return INTERNAL.setCarverSeed(worldSeed, chunkX, chunkZ, version);
	}

	public static long getRegionSeed(long worldSeed, int regionX, int regionZ, int salt, MCVersion version) {
		return INTERNAL.setRegionSeed(worldSeed, regionX, regionZ, salt, version);
	}

	public static long getSlimeSeed(long worldSeed, int chunkX, int chunkZ, long scrambler, MCVersion version) {
		return INTERNAL.setSlimeSeed(worldSeed, chunkX, chunkZ, scrambler, version);
	}

	public static long getSlimeSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		return INTERNAL.setSlimeSeed(worldSeed, chunkX, chunkZ, version);
	}

}
