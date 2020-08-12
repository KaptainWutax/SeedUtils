package kaptainwutax.seedutils.mc.seed;

import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.lcg.LCG;

public class SeedMixer {

	public static final long A = LCG.MMIX.multiplier;
	public static final long B = LCG.MMIX.addend;

	/**
	 * This magic number allows for finding the other root when solving the quadratic.
	 *  Given y, solve for x:
	 *                              ax^2 + bx = ay^2 + by mod 2^64
	 *                  ax^2 + bx - ay^2 + by = 0 mod 2^64
	 *      (ax^2 + bx - ay^2 + by) / (x - y) = 0 mod 2^64
	 *      ((x - y)(a(x + y) + b)) / (x - y) = 0 mod 2^64
	 *                           a(x + y) + b = 0 mod 2^64
	 *                                  x + y = -b * a' mod 2^64
	 *                                      x = -b * a' - y mod 2^64
	 *
	 * @see SeedMixer#getOtherSolution(long)
	 * */
	public static final long MAGIC = -B * Mth.modInverse(A);

	public final long salt;
	public final int steps;

	public SeedMixer(long salt) {
		this(salt, 1);
	}

	protected SeedMixer(long salt, int steps) {
		this.salt = salt;
		this.steps = steps;
	}

	public long nextSeed(long seed) {
		if(this.steps >= 0) {
			for(int i = 0; i < this.steps; i++) {
				seed = mixSeed(seed, this.salt);
			}
		} else {
			for(int i = 0; i < -this.steps; i++) {
				seed = unmixSeed(seed, this.salt, Solution.EVEN);
			}
		}

		return seed;
	}

	public SeedMixer combine(int steps) {
		return new SeedMixer(this.salt, steps);
	}

	public static long getOtherSolution(long seed) {
		return MAGIC - seed;
	}

	public static long mixSeed(long seed, long salt) {
		seed *= seed * A + B;
		seed += salt;
		return seed;
	}

	public static long unmixSeed(long seed, long salt, Solution solution) {
		// Because A and B are odd, C must be even for a solution to exist.
		if(((seed - salt) & 1) == 1) {
			throw new UnsupportedOperationException("Seed " + seed + " is unreachable with salt " + salt);
		}

		long r = solution.ordinal();

		for(int j = 1; j < 64; j <<= 1) {
			r = r - (A * r * r + B * r + salt - seed) * Mth.modInverse(2 * A * r + B, 64);
		}

		return r;
	}

	public enum Solution {
		EVEN, ODD;

		public static Solution of(long n) {
			return values()[(int)(n & 1)];
		}
	}

}
