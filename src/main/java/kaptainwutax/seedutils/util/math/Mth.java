package kaptainwutax.seedutils.util.math;

public final class Mth {

	public static final long MASK_16 = mask(16);
	public static final long MASK_32 = mask(32);
	public static final long MASK_48 = mask(48);

	public static long pow2(int bits) {
		return 1L << bits;
	}

	public static long mask(int bits) {
		if(bits >= 64) {
			return ~0;
		}

		return pow2(bits) - 1;
	}

	public static long maskSigned(long value, int bits) {
		return value << bits >> bits; //removes top bits and copies sign bits back down
	}

	public static long modInverse(long a, int k) {
		long x = ((((a << 1) ^ a) & 4) << 1) ^ a;

		x += x - a * x * x;
		x += x - a * x * x;
		x += x - a * x * x;
		x += x - a * x * x;

		return x & Mth.mask(k);
	}

	public static int max(int... values) {
		if(values.length == 0) {
			throw new UnsupportedOperationException();
		}

		int max = values[0];

		for(int i = 1; i < values.length; i++) {
			max = Math.max(max, values[i]);
		}

		return max;
	}

}
