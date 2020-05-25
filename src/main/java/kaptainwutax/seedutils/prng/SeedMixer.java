package kaptainwutax.seedutils.prng;

import kaptainwutax.seedutils.mc.seed.WorldSeed;
import kaptainwutax.seedutils.prng.lcg.LCG;
import kaptainwutax.seedutils.util.math.Mth;

import java.util.SortedSet;

public class SeedMixer {

	public static final long A = LCG.MMIX.multiplier;
	public static final long B = LCG.MMIX.addend;

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
			// Because a and b are odd, c must be even for a solution to exist.
			if(((seed - this.salt) & 1) == 1) {
				throw new UnsupportedOperationException("Seed " + seed + " is unreachable with salt " + this.salt);
			}

			for(int i = 0; i < -this.steps; i++) {
				seed = unmixSeed(seed, this.salt, Solution.EVEN);
			}
		}

		return seed;
	}

	public SeedMixer combine(int steps) {
		return new SeedMixer(this.salt, steps);
	}

	public static long mixSeed(long seed, long salt) {
		seed *= seed * A + B;
		seed += salt;
		return seed;
	}

	public static long unmixSeed(long seed, long salt, Solution solution) {
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
