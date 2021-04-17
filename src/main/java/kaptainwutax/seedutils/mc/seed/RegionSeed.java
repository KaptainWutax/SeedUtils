package kaptainwutax.seedutils.mc.seed;

public final class RegionSeed {

	public static final long A = 341873128712L;
	public static final long B = 132897987541L;

	public static long getIncrement(int dRegionX, int dRegionZ) {
		return A * dRegionX + B * dRegionZ;
	}

	public static long toWorldSeed(long regionSeed, int regionX, int regionZ, int salt) {
		return RegionSeed.translate(regionSeed, regionX, regionZ) - salt;
	}

	public static long translate(long regionSeed, int dRegionX, int dRegionZ) {
		return regionSeed - RegionSeed.getIncrement(dRegionX, dRegionZ);
	}

	public static long getNeighbor(long regionSeed, int dRegionX, int dRegionZ) {
		return regionSeed + RegionSeed.getIncrement(dRegionX, dRegionZ);
	}

	public static boolean areNeighbors(long regionSeed1, long regionSeed2, int dRegionX, int dRegionZ) {
		return areNeighbors(regionSeed1, regionSeed2, 0, 0, dRegionX, dRegionZ);
	}

	public static boolean areNeighbors(long regionSeed1, long regionSeed2, int salt1, int salt2, int dRegionX, int dRegionZ) {
		return RegionSeed.getIncrement(dRegionX, dRegionZ) == (regionSeed2 - salt2) - (regionSeed1 - salt1);
	}

}
