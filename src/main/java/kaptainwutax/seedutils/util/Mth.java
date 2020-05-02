package kaptainwutax.seedutils.util;

public class Mth {

	private static long pow2(int bits) {
		return 1L << bits;
	}

	public static long mask(int bits) {
		return pow2(bits) - 1;
	}

	public static long maskSigned(long number, int bits) {
		return number << bits >> bits; //removes top bits and copies sign bits back down
	}

	public static long modInverse(long a, long k) {
		long x = ((((a << 1) ^ a) & 4) << 1) ^ a;

		x += x - a * x * x;
		x += x - a * x * x;
		x += x - a * x * x;
		x += x - a * x * x;

		return x & ((1L << k) - 1);
	}

}
