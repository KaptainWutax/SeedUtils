package kaptainwutax.seedutils.mc;

import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.UnsupportedVersion;
import kaptainwutax.seedutils.util.math.Mth;

public class ChunkRand extends JRand {

	public ChunkRand() {
		super(0L, false);
	}

	public ChunkRand(long seed) {
		super(seed);
	}

	public ChunkRand(long seed, boolean scramble) {
		super(seed, scramble);
	}

	/**
	 * Seeds the randomizer to generate the surface terrain blocks (such as grass, sand, etc.) and
	 * the bedrock patterns. Note that the terrain seed does not depend on the world seed and is only
	 * affected by chunk coordinates.
	 *
	 * @param chunkX The X coordinate of the chunk
	 * @param chunkZ The Z coordinate of the chunk
	 * @param version The Minecraft version to use by the algorithm
	 * @return The terrain seed
	 */
	public long setTerrainSeed(int chunkX, int chunkZ, MCVersion version) {
		long seed = (long)chunkX * 341873128712L + (long)chunkZ * 132897987541L;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	/**
	 * Seeds the randomizer to create population features such as decorators and animals.
	 *
	 * The parameters named "x" and "z" may seem ambiguous but there is a good reason behind it.
	 * In 1.13+, this method takes in the world seed and the negative-most block coordinates of the
	 * chunk. The coordinate pair provided is equivalent to (chunkX * 16, chunkZ * 16). In older
	 * versions though, the method takes in chunk coordinates.
	 *
	 * Additionally, in 1.13, the {@code / 2L * 2L + 1L} operation on {@code nextLong()} was replaced
	 * with a much cleaner {| 1L}. These two operations may seem to be same, but in reality, the former
	 * behaves differently depending on the sign of said {@code nextLong()}.
	 *
	 * This function has been proved to be reversible through some exploitation of the underlying
	 * nextLong() weaknesses.
	 * 		
	 * @param worldSeed The world seed (or at the very least its 48 lowest bits)
	 * @param x The X coordinate
	 * @param z The Z coordinate
	 * @param version The Minecraft version to use by the algorithm
	 * @return The population seed
	 */
	public long setPopulationSeed(long worldSeed, int x, int z, MCVersion version) {
		this.setSeed(worldSeed);
		long a, b;

		if(version.isOlderThan(MCVersion.v1_13)) {
			a = this.nextLong() / 2L * 2L + 1L;
			b = this.nextLong() / 2L * 2L + 1L;
		} else {
			a = this.nextLong() | 1L;
			b = this.nextLong() | 1L;
		}

		long seed = (long)x * a + (long)z * b ^ worldSeed;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	/**
	 * Seeds the randomizer to generate a given decoration feature.
	 *
	 * The salt, in the form of {@code index + 10000 * step} assures that each feature is seeded
	 * differently, making the decoration feel more random. Even though it does a good job
	 * at doing so, many entropy issues arise from the salt being so small and result in
	 * weird alignments between features that have an index close apart. This method did NOT
	 * exist pre 1.13 since all features used the same {@param populationSeed}.
	 *
	 * @see ChunkRand#setPopulationSeed(long, int, int, MCVersion)
	 *
	 * @param populationSeed The population seed (or at the very least its 48 lowest bits)
	 * @param index The index of the feature in the biome feature list
	 * @param step The generation step used by the generator
	 * @param version The Minecraft version to use by the algorithm
	 * @return The decorator seed
	 */
	public long setDecoratorSeed(long populationSeed, int index, int step, MCVersion version) {
		if(version.isOlderThan(MCVersion.v1_13)) {
			throw new UnsupportedVersion(version, "decorator seed");
		}

		long seed = populationSeed + (long)index + (long)(10000 * step);
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	/**
	 * @see ChunkRand#setDecoratorSeed(long, int, int, MCVersion)
	 *
	 * @param worldSeed The population seed (or at the very least its 48 lowest bits)
	 * @param blockX The X coordinate of the negative-most block in the chunk
	 * @param blockZ The Z coordinate of the negative-most block in the chunk
	 * @param index The index of the feature in the biome feature list
	 * @param step The generation step used by the generator
	 * @param version The Minecraft version to use by the algorithm
	 * @return The decorator seed
	 */
	public long setDecoratorSeed(long worldSeed, int blockX, int blockZ, int index, int step, MCVersion version) {
		long populationSeed = this.setPopulationSeed(worldSeed, blockX, blockZ, version);
		return this.setDecoratorSeed(populationSeed, index, step, version);
	}

	/**
	 * Seeds the randomizer to generate larger features such as caves, ravines, mineshafts and
	 * strongholds. It is also used to initiate structure start behaviour such as rotation and loot.
	 *
	 * @param worldSeed The world seed (or at the very least its 48 lowest bits)
	 * @param chunkX The X coordinate of the chunk
	 * @param chunkZ The Z coordinate of the chunk
	 * @param version The Minecraft version to use by the algorithm
	 * @return The carver seed
	 */
	public long setCarverSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		this.setSeed(worldSeed);
		long a = this.nextLong();
		long b = this.nextLong();
		long seed = (long)chunkX * a ^ (long)chunkZ * b ^ worldSeed;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	/**
	 * Seeds the randomizer to determine the start position of structure features such as temples,
	 * monuments and buried treasures within a region.
	 *
	 * The region coordinates pair corresponds to the coordinates of the region the seeded chunk
	 * lies in. For example, a swamp hut region is 32 by 32 chunks meaning that all chunks that
	 * lie within that region get seeded the same way.
	 *
	 * @param worldSeed The world seed (or at the very least its 48 lowest bits)
	 * @param regionX The X coordinate of the region the chunk lies in
	 * @param regionZ The Z coordinate of the region the chunk lies in
	 * @param salt The salt value for the structure
	 * @param version The Minecraft version to use by the algorithm
	 * @return The region seed
	 */
	public long setRegionSeed(long worldSeed, int regionX, int regionZ, int salt, MCVersion version) {
		long seed = (long)regionX * 341873128712L + (long)regionZ * 132897987541L + worldSeed + (long)salt;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	/**
	 * This method is not explicitly declared in the Minecraft codebase but is used by nether fortresses
	 * and pillager outposts to negate a spawn attempt. This is by far the weakest hash of the set
	 * since it has horrible coordinate collisions.
	 *
	 * @param worldSeed The world seed (or at the very least its 48 lowest bits)
	 * @param chunkX The X coordinate of the chunk
	 * @param chunkZ The Z coordinate of the chunk
	 * @param version The Minecraft version to use by the algorithm
	 * @return The weak seed
	 */
	public long setWeakSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		int sX = chunkX >> 4;
		int sZ = chunkZ >> 4;
		long seed = (long)(sX ^ sZ << 4) ^ worldSeed;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	/**
	 * Seeds the randomizer to decide if a chunk should allow for slime spawns. Since it
	 * is only used in one place, the {@param scrambler} only ever takes one value.
	 *
	 * @see ChunkRand#setSlimeSeed(long, int, int, MCVersion)
	 *
	 * @param worldSeed The world seed (or at the very least its 48 lowest bits)
	 * @param chunkX The X coordinate of the chunk
	 * @param chunkZ The Z coordinate of the
	 * @param scrambler The value to XOR the sum with (currently only ever equal to 987234911 in the codebase)
	 * @param version The Minecraft version to use by the algorithm
	 * @return The slime seed
	 */
	public long setSlimeSeed(long worldSeed, int chunkX, int chunkZ, long scrambler, MCVersion version) {
		long seed = worldSeed + (long)(chunkX * chunkX * 4987142)
				+ (long)(chunkX * 5947611) + (long)(chunkZ * chunkZ) * 4392871L + (long)(chunkZ * 389711) ^ scrambler;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	/**
	 * @see ChunkRand#setSlimeSeed(long, int, int, long, MCVersion)
	 *
	 * @param worldSeed The world seed (or at the very least its 48 lowest bits)
	 * @param chunkX The X coordinate of the chunk
	 * @param chunkZ The Z coordinate of the
	 * @param version The Minecraft version to use by the algorithm
	 * @return The slime seed used for slime chunks
	 */
	public long setSlimeSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		return this.setSlimeSeed(worldSeed, chunkX, chunkZ, 987234911L, version);
	}

}
