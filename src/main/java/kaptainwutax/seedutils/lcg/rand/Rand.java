package kaptainwutax.seedutils.lcg.rand;

import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.lcg.LCG;

public class Rand {

    protected long counter=0;
    private final LCG lcg;
    private long seed;

    protected Rand(LCG lcg) {
        this.lcg = lcg;
    }

    public Rand(LCG lcg, long seed) {
        this(lcg);
        this.setSeed(seed);
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.counter=0;
    }

    public long getCounter() {
        return counter;
    }

    public long getSeed() {
        return this.seed;
    }

    public long nextSeed() {
        this.counter++;
        return this.seed = this.lcg.nextSeed(this.seed);
    }

    public long nextBits(int bits) {
        this.seed = this.nextSeed();

        if(this.lcg.isModPowerOf2()) {
            return this.seed >>> (this.lcg.getModTrailingZeroes() - bits);
        }

        return this.seed / Mth.getPow2(bits);
    }

    public void advance(long calls) {
        this.counter+=calls;
        this.advance(this.lcg.combine(calls));
    }

    public void advance(LCG skip) {
        // this call if called directly is not covered by the counter
        this.seed = skip.nextSeed(this.seed);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)return true;
        if(!(o instanceof Rand))return false;
        Rand rand = (Rand)o;
        return this.getSeed() == rand.getSeed() && this.lcg.equals(rand.lcg);
    }

    @Override
    public int hashCode() {
        return (int)(this.lcg.hashCode() + this.seed);
    }

    @Override
    public String toString() {
        return "Rand{" + "seed=" + this.seed + '}';
    }

}

