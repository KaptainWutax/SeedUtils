package kaptainwutax.seedutils.mc;

import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.Mth;
import kaptainwutax.seedutils.util.UnsupportedMCVersion;

public class ChunkRand extends JRand {

	protected int sampleCount;

	public ChunkRand() {
		super(0L, false);
	}

	public ChunkRand(long seed) {
		super(seed);
	}

	public ChunkRand(long seed, boolean scramble) {
		super(seed, scramble);
	}

	public int getSampleCount() {
		return this.sampleCount;
	}

	@Override
	public int next(int bits) {
		this.sampleCount++;
		return super.next(bits);
	}

	public long setTerrainSeed(int chunkX, int chunkZ, MCVersion version) {
		long seed = (long)chunkX * 341873128712L + (long)chunkZ * 132897987541L;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

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

	public long setDecoratorSeed(long populationSeed, int index, int step, MCVersion version) {
		if(version.isOlderThan(MCVersion.v1_13)) {
			throw new UnsupportedMCVersion("decorator seed", version);
		}

		long seed = populationSeed + (long)index + (long)(10000 * step);
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	public long setDecoratorSeed(long worldSeed, int blockX, int blockZ, int index, int step, MCVersion version) {
		long populationSeed = this.setPopulationSeed(worldSeed, blockX, blockZ, version);
		return this.setDecoratorSeed(populationSeed, index, step, version);
	}

	public long setCarverSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		this.setSeed(worldSeed);
		long a = this.nextLong();
		long b = this.nextLong();
		long seed = (long)chunkX * a ^ (long)chunkZ * b ^ worldSeed;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	public long setRegionSeed(long worldSeed, int regionX, int regionZ, int salt, MCVersion version) {
		long seed = (long)regionX * 341873128712L + (long)regionZ * 132897987541L + worldSeed + (long)salt;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	public long setWeakSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		int sX = chunkX >> 4;
		int sZ = chunkZ >> 4;
		long seed = (long)(sX ^ sZ << 4) ^ worldSeed;
		this.setSeed(seed);
		return seed;
	}

	public long setSlimeSeed(long worldSeed, int chunkX, int chunkZ, long scrambler, MCVersion version) {
		long seed = worldSeed + (long)(chunkX * chunkX * 4987142)
				+ (long)(chunkX * 5947611) + (long)(chunkZ * chunkZ) * 4392871L + (long)(chunkZ * 389711) ^ scrambler;
		this.setSeed(seed);
		return seed & Mth.MASK_48;
	}

	public long setSlimeSeed(long worldSeed, int chunkX, int chunkZ, MCVersion version) {
		return this.setSlimeSeed(worldSeed, chunkX, chunkZ, 987234911L, version);
	}

}
