package kaptainwutax.seedutils.util.math;

import java.util.Arrays;
import java.util.function.Consumer;

public final class Mth {

	public static final long MASK_16 = mask(16);
	public static final long MASK_32 = mask(32);
	public static final long MASK_48 = mask(48);

	public static final long[] FACTORIAL = new long[21];

	static {
		FACTORIAL[0] = 1;

		for(int i = 1; i < FACTORIAL.length; i++) {
			FACTORIAL[i] = FACTORIAL[i - 1] * i;
		}
	}

	public static long pow2(int bits) {
		return 1L << bits;
	}

	public static boolean isPowerOf2(long value) {
		return (value & -value) == value;
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

	public static long factorial(int n) {
		return FACTORIAL[n];
	}

	public static long modInverse(long a) {
		return modInverse(a, 64);
	}

	public static long modInverse(long a, int k) {
		long x = ((((a << 1) ^ a) & 4) << 1) ^ a;

		x += x - a * x * x;
		x += x - a * x * x;
		x += x - a * x * x;
		x += x - a * x * x;

		return x & Mth.mask(k);
	}

	public static int min(int... values) {
		int min = values[0];

		for(int i = 1; i < values.length; i++) {
			min = Math.min(min, values[i]);
		}

		return min;
	}

	public static int max(int... values) {
		int max = values[0];

		for(int i = 1; i < values.length; i++) {
			max = Math.max(max, values[i]);
		}

		return max;
	}

	public static int floor(double d) {
		int i = (int)d;
		return d < (double)i ? i - 1 : i;
	}

	public static float clamp(int value, int min, int max) {
		if(value < min)return min;
		return Math.min(value, max);
	}

	public static float clamp(float value, float min, float max) {
		if(value < min)return min;
		return Math.min(value, max);
	}

	public static long getPermutations(int n, int k) {
		return factorial(n) / factorial(n - k);
	}

	public static void permute(int n, int k, Consumer<int[]> action) {
		for(long perm = 0; perm < factorial(n); perm += factorial(n - k)) {
			long permCopy = perm;
			int[] indices = new int[n];
			int[] permutation = new int[n];

			for(int i = 0; i < n; i++) {
				indices[i] = (int)(permCopy / factorial(n - 1 - i));
				permCopy -= indices[i] * factorial(n - 1 - i);
			}

			for(int i = 0; i < k; i++) {
				int wantedIndex = indices[i];
				int currentIndex = 0;

				for(int j = 0; j < permutation.length; j++) {
					if(permutation[j] != 0)continue;
					if(currentIndex++ == wantedIndex) {
						currentIndex = j;
						break;
					}
				}

				permutation[currentIndex] = i + 1;
			}

			action.accept(permutation);
		}
	}

}
