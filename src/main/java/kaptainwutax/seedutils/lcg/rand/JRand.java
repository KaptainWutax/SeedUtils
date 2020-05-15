package kaptainwutax.seedutils.lcg.rand;

import kaptainwutax.seedutils.lcg.LCG;

import java.util.Random;

public class JRand extends Rand {

	private static final double DOUBLE_UNIT = 0x1.0p-53;

	private double nextNextGaussian;
	private boolean haveNextNextGaussian;

	protected JRand(LCG lcg, long seed) {
		super(lcg, seed);
	}

	protected JRand(LCG lcg, long seed, boolean scramble) {
		super(lcg);
		this.setSeed(seed, scramble);
	}

	public JRand(long seed) {
		this(LCG.JAVA, seed, true);
	}

	public JRand(long seed, boolean scramble) {
		super(LCG.JAVA);
		this.setSeed(seed, scramble);
	}

	public static JRand ofInternalSeed(long seed) {
		return new JRand(seed, false);
	}

	public static JRand ofScrambledSeed(long seed) {
		return new JRand(seed, true);
	}

	@Override
	public void setSeed(long seed) {
		this.setSeed(seed, true);
	}

	public void setSeed(long seed, boolean scramble) {
		if(scramble)super.setSeed(seed ^ LCG.JAVA.multiplier);
		else super.setSeed(seed);
	}

	public int next(int bits) {
		return (int)this.nextBits(bits);
	}

	public boolean nextBoolean() {
		return this.next(1) == 1;
	}

	public int nextInt() {
		return this.next(32);
	}

	public int nextInt(int bound) {
		if(bound <= 0) {
			throw new IllegalArgumentException("bound must be positive");
		}

		if((bound & -bound) == bound) {
			return (int)((bound * (long)this.next(31)) >> 31);
		}

		int bits, value;

		do {
			bits = this.next(31);
			value = bits % bound;
		} while(bits - value + (bound - 1) < 0);

		return value;
	}

	public float nextFloat() {
		return this.next(24) / ((float)(1 << 24));
	}

	public long nextLong() {
		return ((long)this.next(32) << 32) + this.next(32);
	}

	public double nextDouble() {
		return (((long)(this.next(26)) << 27) + next(27)) * DOUBLE_UNIT;
	}

	public double nextGaussian() {
		if(this.haveNextNextGaussian) {
			this.haveNextNextGaussian = false;
			return this.nextNextGaussian;
		} else {
			double v1, v2, s;

			do {
				v1 = 2 * nextDouble() - 1; // between -1 and 1
				v2 = 2 * nextDouble() - 1; // between -1 and 1
				s = v1 * v1 + v2 * v2;
			} while (s >= 1 || s == 0);

			double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s)/s);
			this.nextNextGaussian = v2 * multiplier;
			this.haveNextNextGaussian = true;
			return v1 * multiplier;
		}
	}

	public CombinedJRand combine(long steps) {
		return new CombinedJRand(steps, this.getSeed(), false);
	}

	public JRand copy() {
		return new JRand(this.getSeed(), false);
	}

	public Random asRandomView() {
		return new JRand.RandomWrapper(this);
	}

	public Random copyToRandom() {
		return this.copy().asRandomView();
	}

	public Random toRandom() {
		return new Random(this.getSeed() ^ LCG.JAVA.multiplier);
	}

	private static final class RandomWrapper extends Random {
		private final JRand delegate;

		private RandomWrapper(JRand delegate) {
			this.delegate = delegate;
		}

		@Override
		protected int next(int bits) {
			return this.delegate.next(bits);
		}

		@Override
		public void setSeed(long seed) {
			this.delegate.setSeed(seed);
		}
	}

}
