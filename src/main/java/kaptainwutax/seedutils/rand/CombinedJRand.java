package kaptainwutax.seedutils.rand;

import kaptainwutax.seedutils.lcg.LCG;

public class CombinedJRand extends JRand {

	private final long steps;

	public CombinedJRand(long steps, long seed) {
		this(steps, seed, true);
	}

	public CombinedJRand(long steps, long seed, boolean scramble) {
		super(LCG.JAVA.combine(steps), seed, scramble);
		this.steps = steps;
	}

	public long getSteps() {
		return this.steps;
	}

	@Override
	public CombinedJRand combine(long steps) {
		return new CombinedJRand(this.steps * steps, this.getSeed(), false);
	}

}
