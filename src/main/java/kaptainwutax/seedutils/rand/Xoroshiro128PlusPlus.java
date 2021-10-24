package kaptainwutax.seedutils.rand;

import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
public class Xoroshiro128PlusPlus implements IRand {
	public Seed128 seed;
	public static final long GOLDEN_RATIO_64 = -7046029254386353131L;
	public static final long SILVER_RATIO_64 = 7640891576956012809L;
	private static final AtomicLong SEED_UNIQUIFIER = new AtomicLong(8682522807148012L);
	private double nextNextGaussian;
	private boolean haveNextNextGaussian;

	// http://zimbry.blogspot.com/2011/09/better-bit-mixing-improving-on.html
	// Also named mix64variant13 which is David Stafford’s Mix13 variant of the
	// MurmurHash3 finalizer
	public static long mixStafford13(long l) {
		l = (l ^ l >>> 30) * -4658895280553007687L;
		l = (l ^ l >>> 27) * -7723592293110705685L;
		return l ^ l >>> 31;
	}

	public static Seed128 upgradeSeedTo128bit(long seed) {
		long silverSeed = seed ^ SILVER_RATIO_64;
		long goldenSeed = silverSeed + GOLDEN_RATIO_64;
		return new Seed128(mixStafford13(silverSeed), mixStafford13(goldenSeed));
	}

	public static long seedUniquifier() {
		return SEED_UNIQUIFIER.updateAndGet(l -> l * 1181783497276652981L) ^ System.nanoTime();
	}

	public Xoroshiro128PlusPlus(long loSeed, long hiSeed) {
		this.seed = new Seed128(loSeed, hiSeed);
		if ((loSeed | hiSeed) == 0L) {
			//  the fractional parts of square roots of squarefree numbers *2^64.
			this.seed = new Seed128(-7046029254386353131L, 7640891576956012809L);
		}
	}

	public Xoroshiro128PlusPlus(long seed) {
		this.seed = upgradeSeedTo128bit(seed);
	}

	public int nextInt() {
		return (int) this.nextLong();
	}


	public int nextInt(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Bound must be positive");
		}
		return Math.abs((int) (this.nextLong() % (long) n));
	}


	public boolean nextBoolean() {
		return (this.nextLong() & 1L) != 0L;
	}


	public float nextFloat() {
		return (float) this.nextBits(24) * 5.9604645E-8f;
	}


	public double nextDouble() {
		return (double) this.nextBits(53) * 1.1102230246251565E-16;
	}


	public void consumeCount(int n) {
		for (int i = 0; i < n; ++i) {
			this.nextLong();
		}
	}

	public double nextGaussian() {
		if (this.haveNextNextGaussian) {
			this.haveNextNextGaussian = false;
			return this.nextNextGaussian;
		} else {
			double s;
			double v1;
			double v2;
			do {
				v1 = 2.0 * this.nextDouble() - 1.0; // between -1 and 1
				v2 = 2.0 * this.nextDouble() - 1.0; // between -1 and 1
				s = v1 * v1 + v2 * v2;
			} while (s >= 1.0 || s == 0.0);
			double multiplier = StrictMath.sqrt(-2.0 * StrictMath.log(s) / s);
			this.nextNextGaussian = v2 * multiplier;
			this.haveNextNextGaussian = true;
			return v1 * multiplier;
		}
	}

	private long nextBits(int n) {
		return this.nextLong() >>> 64 - n;
	}

	public long nextLong() {
		long lowSeed = this.seed.loSeed;
		long hiSeed = this.seed.hiSeed;
		long res = Long.rotateLeft(lowSeed + hiSeed, 17) + lowSeed;

		this.seed.loSeed = Long.rotateLeft(lowSeed, 49) ^ (hiSeed ^= lowSeed) ^ (hiSeed << 21);
		this.seed.hiSeed = Long.rotateLeft(hiSeed, 28);
		return res;
	}

	public static class Seed128 {
		public long hiSeed;
		public long loSeed;

		public Seed128(long lo, long hi) {
			loSeed = lo;
			hiSeed = hi;
		}

		@Override
		public String toString() {
			return "Seed128{" +
					"hiSeed=" + hiSeed +
					", loSeed=" + loSeed +
					'}';
		}
	}
}
