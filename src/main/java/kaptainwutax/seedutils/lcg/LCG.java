package kaptainwutax.seedutils.lcg;

import java.util.Objects;

public class LCG {

    public static final LCG VISUAL_BASIC = new LCG(1140671485L, 12820163L, 1L << 24);
    public static final LCG RANDU = new LCG(65539L, 0L, 1L << 31);
    public static final LCG GLIBC = new LCG(1103515245L, 12345L, 1L << 31);
    public static final LCG BORLAND_C = new LCG(22695477L, 1L, 1L << 32);
    public static final LCG TURBO_PASCAL = new LCG(134775813L, 1L, 1L << 32);
    public static final LCG OPEN_VMS = new LCG(69069L, 1L, 1L << 32);
    public static final LCG JAVA = new LCG(0x5DEECE66DL, 0xBL, 1L << 48);
    public static final LCG MMIX = new LCG(6364136223846793005L, 1442695040888963407L);
    public static final LCG NEWLIB_C = new LCG(6364136223846793005L, 1L);

    public final long multiplier;
    public final long addend;
    public final long modulus;

    private final boolean isPowerOf2;
    private final int trailingZeros;

    public LCG(long multiplier, long addend) { //Modulus is 2^64.
        this(multiplier, addend, 0);
    }

    public LCG(long multiplier, long addend, long modulus) {
        this.multiplier = multiplier;
        this.addend = addend;
        this.modulus = modulus;

        this.isPowerOf2 = (this.modulus & -this.modulus) == this.modulus;
        this.trailingZeros = this.isPowerOf2 ? Long.numberOfTrailingZeros(this.modulus) : -1;
    }

    public boolean isPowerOf2() {
        return this.isPowerOf2;
    }

    public int getTrailingZeroes() {
        return this.trailingZeros;
    }

    public long nextSeed(long seed) {
        return this.mod(seed * this.multiplier + this.addend);
    }

    public LCG combine(long steps) {
        long multiplier = 1;
        long addend = 0;

        long intermediateMultiplier = this.multiplier;
        long intermediateAddend = this.addend;

        for(long k = steps; k != 0; k >>>= 1) {
            if((k & 1) != 0) {
                multiplier *= intermediateMultiplier;
                addend = intermediateMultiplier * addend + intermediateAddend;
            }

            intermediateAddend = (intermediateMultiplier + 1) * intermediateAddend;
            intermediateMultiplier *= intermediateMultiplier;
        }

        multiplier = this.mod(multiplier);
        addend = this.mod(addend);

        return new LCG(multiplier, addend, this.modulus);
    }

    public LCG invert() {
        return this.combine(-1);
    }

    public long mod(long n) {
        if(this.isPowerOf2()) {
            return n & (this.modulus - 1);
        } else if(n <= 1L << 32) {
            return Long.remainderUnsigned(n, this.modulus);
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(!(obj instanceof LCG))return false;
        LCG lcg = (LCG)obj;
        return this.multiplier == lcg.multiplier &&
                this.addend == lcg.addend &&
                this.modulus == lcg.modulus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.multiplier, this.addend, this.modulus);
    }

    @Override
    public String toString() {
        return "LCG{" + "multiplier=" + this.multiplier +
                ", addend=" + this.addend + ", modulo=" + this.modulus + '}';
    }

}
