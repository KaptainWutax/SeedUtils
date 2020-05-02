package kaptainwutax.seedutils.lcg.rand;

import kaptainwutax.seedutils.lcg.LCG;

public class CombinedJRand extends JRand {

	private final int steps;

	public CombinedJRand(int steps, long seed) {
		this(steps, seed, true);
	}

	public CombinedJRand(int steps, long seed, boolean scramble) {
		super(LCG.JAVA.combine(steps), seed, scramble);
		this.steps = steps;
	}

	public int getSteps() {
		return this.steps;
	}

}
