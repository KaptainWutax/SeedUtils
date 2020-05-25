package kaptainwutax.seedutils.util.math;

import java.util.Arrays;

public final class Polynomial {

	private final long[] coefficients;
	private int degree = -1;

	private Polynomial derivative;

	public Polynomial(long... coefficients) {
		this.coefficients = coefficients;
		this.computeDegree();
	}

	public Polynomial(long coefficient, int exponent) {
		this.coefficients = new long[exponent + 1];
		this.coefficients[exponent] = coefficient;
		this.degree = exponent;
	}

	private void computeDegree() {
		for(int i = this.coefficients.length - 1; i >= 0; i--) {
			if(this.coefficients[i] == 0)continue;
			this.degree = i;
			break;
		}
	}

	public int degree() {
		return this.degree;
	}

	public long coefficient(int exponent) {
		return this.coefficients[exponent];
	}

	public long[] coefficients() {
		return Arrays.copyOf(this.coefficients, this.coefficients.length);
	}

	public long evaluate(long point) {
		long result = 0;

		for(int i = this.degree; i >= 0; i--) {
			result = this.coefficients[i] + (point * result);
		}

		return result;
	}

	public Polynomial differentiate() {
		if(this.derivative != null)return this.derivative;
		if(this.degree <= 0)return new Polynomial(0, 0);

		Polynomial r = new Polynomial(0, this.degree - 1);

		for(int e = 1; e < this.degree; e++) {
			r.coefficients[e - 1] =  this.coefficients[e] * e;
		}

		return this.derivative = r;
	}

	public Polynomial add(Polynomial p) {
		Polynomial r = new Polynomial(0, Math.max(this.degree, p.degree));

		for (int i = 0; i <= r.degree; i++) {
			if(i <= this.degree) {
				r.coefficients[i] = this.coefficients[i];
			}

			if(i <= p.degree) {
				r.coefficients[i] += p.coefficients[i];
			}
		}

		r.computeDegree();
		return r;
	}

	public Polynomial multiply(Polynomial p) {
		Polynomial r = new Polynomial(0, this.degree + p.degree);

		for(int i = 0; i <= this.degree; i++) {
			for(int j = 0; j <= p.degree; j++) {
				r.coefficients[i + j] += this.coefficients[i] * p.coefficients[j];
			}
		}

		r.computeDegree();
		return r;
	}

	public Polynomial compose(Polynomial p) {
		Polynomial r = new Polynomial(0, 0);

		for(int i = this.degree; i >= 0; i--) {
			Polynomial t = new Polynomial(this.coefficients[i], 0);
			r = t.add(p.multiply(r));
		}

		return r;
	}

	@Override
	public String toString() {
		if(this.degree < 0) {
			return "0";
		}

		StringBuilder sb = new StringBuilder();

		for(int i = this.degree; i >= 0; i--) {
			long c = this.coefficients[i];
			int sign = c < 0 ? -1 : 1;
			c *= sign;

			if(i != this.degree) {
				sb.append(sign == 1 ? " + " : " - ");
			}

			sb.append(Long.toUnsignedString(c));

			if(i != 0) {
				sb.append("x");
				if(i != 1)sb.append("^").append(i);
			}
		}

		return sb.toString();
	}

}
