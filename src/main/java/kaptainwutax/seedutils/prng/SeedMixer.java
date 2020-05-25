package kaptainwutax.seedutils.prng;

import kaptainwutax.seedutils.prng.lcg.LCG;
import kaptainwutax.seedutils.util.math.Mth;

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

	public static long mixSeed(long seed, long salt) {
		seed *= seed * A + B;
		seed += salt;
		return seed;
	}

	public long nextSeed(long seed) {
		if(this.steps >= 0) {
			for(int i = 0; i < this.steps; i++) {
				seed = mixSeed(seed, this.salt);
			}
		} else {
			for(int i = 0; i < -this.steps; i++) {
				seed = solve(seed, this.salt)[0];
			}
		}

		return seed;
	}

	public SeedMixer combine(int steps) {
		return new SeedMixer(this.salt, steps);
	}

	public static long[] solve(long seed, long salt) {
		long c = salt - seed;

		// Because a and b are odd, c must be even for a solution to exist.
		if((c & 1) == 1) {
			System.err.println("ur bad");
			return new long[0];
		}

		//We expect 2 solutions, one even and one odd.
		// i = 0 gives the even solution, i = 1 gives the odd solution.
		// We only care about the even solution 99.9% of the time.
		return new long[] {solveRoot(c, 0), solveRoot(c, 1)};
	}

	public static long solveRoot(long c, long root) {
		for(int j = 1; j < 64; j <<= 1) {
			root = root - (A * root * root + B * root + c) * Mth.modInverse(2 * A * root + B, 64);
		}

		return root;
	}

}
