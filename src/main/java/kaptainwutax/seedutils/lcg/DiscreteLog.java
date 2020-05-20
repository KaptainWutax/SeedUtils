package kaptainwutax.seedutils.lcg;

import kaptainwutax.seedutils.util.math.Mth;

import java.math.BigInteger;

public class DiscreteLog {

	private static final BigInteger ONE = BigInteger.valueOf(1L);
	private static final BigInteger TWO = BigInteger.valueOf(2L);

	public static boolean supports(LCG lcg) {
		if(!lcg.isModPowerOf2() || lcg.getModTrailingZeroes() > 61)return false;
		if(lcg.multiplier % 2 == 0 || lcg.addend % 2 == 0)return false;
		return true;
	}

	public static long distanceFromZero(LCG lcg, long seed) {
		int exp = lcg.getModTrailingZeroes();

		long a = lcg.multiplier;
		long b = (((seed * (lcg.multiplier - 1)) * Mth.modInverse(lcg.addend, exp)) + 1) & ((1L << (exp + 2)) - 1);
		long aBar = theta(a, exp);
		long bBar = theta(b, exp);
		return bBar * Mth.modInverse(aBar, exp) & Mth.mask(exp);
	}

	private static long theta(long number, int exp) {
		if(number % 4 == 3) {
			number = (1L << (exp + 2)) - number;
		}

		BigInteger xHat = BigInteger.valueOf(number);
		xHat = xHat.modPow(TWO.pow(exp + 1), TWO.pow(2 * exp + 3));
		xHat = xHat.subtract(ONE);
		xHat = xHat.divide(TWO.pow(exp + 3));
		xHat = xHat.mod(TWO.pow(exp));
		return xHat.longValue();
	}

}
