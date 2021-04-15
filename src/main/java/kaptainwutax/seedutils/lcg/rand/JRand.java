package kaptainwutax.seedutils.lcg.rand;

import kaptainwutax.seedutils.lcg.LCG;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

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
        if (scramble) super.setSeed(seed ^ LCG.JAVA.multiplier);
        else super.setSeed(seed);
    }

    public int next(int bits) {
        return (int) this.nextBits(bits);
    }

    public boolean nextBoolean() {
        return this.next(1) == 1;
    }

    public int nextInt() {
        return this.next(32);
    }

    public int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }

        if ((bound & -bound) == bound) {
            return (int) ((bound * (long) this.next(31)) >> 31);
        }

        int bits, value;

        do {
            bits = this.next(31);
            value = bits % bound;
        } while (bits - value + (bound - 1) < 0);

        return value;
    }

    public float nextFloat() {
        return this.next(24) / ((float) (1 << 24));
    }

    public long nextLong() {
        return ((long) this.next(32) << 32) + this.next(32);
    }

    public double nextDouble() {
        return (((long) (this.next(26)) << 27) + next(27)) * DOUBLE_UNIT;
    }

    public double nextGaussian() {
        if (this.haveNextNextGaussian) {
            this.haveNextNextGaussian = false;
            return this.nextNextGaussian;
        } else {
            double v1, v2, s;

            do {
                v1 = 2 * nextDouble() - 1; // between -1 and 1
                v2 = 2 * nextDouble() - 1; // between -1 and 1
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);

            double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
            this.nextNextGaussian = v2 * multiplier;
            this.haveNextNextGaussian = true;
            return v1 * multiplier;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void shuffle(List<?> list) {
        int size = list.size();
        if (size < 5 || list instanceof RandomAccess) {
            for (int i=size; i>1; i--)
                swap(list, i-1, this.nextInt(i));
        } else {
            Object[] arr = list.toArray();
            for (int i=size; i>1; i--)
                swap(arr, i-1, this.nextInt(i));
            ListIterator it = list.listIterator();
            for (Object e : arr) {
                it.next();
                it.set(e);
            }
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

    public JRand.Debugger asDebugger() {
        return new JRand.Debugger(this);
    }

    public Random copyToRandom() {
        return this.copy().asRandomView();
    }

    public Random toRandom() {
        return new Random(this.getSeed() ^ LCG.JAVA.multiplier);
    }

    public static boolean nextBoolean(long seed) {
        return ((seed >>> 47) & 1) == 1;
    }

    public static int nextInt(long seed) {
        return (int) (seed >>> 16);
    }

    public static int nextInt(long seed, int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }

        if ((bound & -bound) == bound) {
            return (int) ((bound * seed) >> 31);
        }

        int bits, value;

        do {
            bits = (int) (seed >>> 17);
            value = bits % bound;
            seed = LCG.JAVA.nextSeed(seed);
        } while (bits - value + (bound - 1) < 0);

        return value;
    }

    public static float nextFloat(long seed) {
        return (int) (seed >>> 24) / ((float) (1 << 24));
    }

    public static long nextLong(long seed) {
        return (seed >>> 16 << 32) + (int) (LCG.JAVA.nextSeed(seed) >>> 16);
    }

    public static double nextDouble(long seed) {
        return (((long) ((int) (seed >>> 22)) << 27) + (int) (LCG.JAVA.nextSeed(seed) >>> 21)) * DOUBLE_UNIT;
    }

    public static void shuffle(List<?> list, JRand rand) {
        rand.shuffle(list);
    }

    public static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void swap(List<?> list, int i, int j) {
        ((List) list).set(i, ((List) list).set(j, ((List) list).get(i)));
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

    public static final class Debugger extends JRand {

        private long globalCounter;
        private long nextIntSkip;
        private boolean hasCalledAdvance;
        private final JRand delegate;

        public Debugger(JRand delegate) {
            super(delegate.getLcg(), delegate.getSeed());
            this.delegate = delegate;
            this.globalCounter = 0;
            this.nextIntSkip = 0;
        }

        @Override
        public long nextSeed() {
            if (this.delegate != null) {
                this.globalCounter++;
                return this.delegate.nextSeed();
            } else {
                return super.nextSeed();
            }
        }

        @Override
        public void setSeed(long seed) {
            if (this.delegate != null) {
                this.globalCounter = 0;
                this.delegate.setSeed(seed);
            } else {
                super.setSeed(seed, false);
            }
        }

        @Override
        public void advance(long calls) {
            if (this.delegate != null) {
                this.globalCounter += calls;
                this.hasCalledAdvance = true;
                this.delegate.advance(calls);
            } else {
                super.advance(calls);
            }
        }

        @Override
        public int nextInt(int bound) {
            if (this.delegate != null) {
                if (bound <= 0) {
                    throw new IllegalArgumentException("bound must be positive");
                }

                if ((bound & -bound) == bound) {
                    this.globalCounter++;
                    return (int) ((bound * (long) this.delegate.next(31)) >> 31);
                }

                int bits, value;
                long oldCounter = this.globalCounter;
                do {
                    this.globalCounter++;
                    bits = this.delegate.next(31);
                    value = bits % bound;
                } while (bits - value + (bound - 1) < 0);
                this.nextIntSkip += this.globalCounter - oldCounter - 1;
                return value;
            } else {
                return super.nextInt(bound);
            }
        }

        @Override
        public int next(int bits) {
            if (this.delegate != null) {
                this.globalCounter++;
                return this.delegate.next(bits);
            } else {
                return super.next(bits);
            }
        }

        @Override
        public void advance(LCG lcg) {
            if (this.delegate != null) {
                long old = this.getSeed();
                this.delegate.advance(lcg);
                if (this.hasCalledAdvance) {
                    this.hasCalledAdvance = false;
                } else {
                    this.globalCounter += LCG.JAVA.distance(old, this.getSeed()) - 1;
                }
            } else {
                super.advance(lcg);
            }
        }

        @Override
        public long getSeed() {
            if (this.delegate != null) {
                return this.delegate.getSeed();
            } else {
                return super.getSeed();
            }

        }

        public long getGlobalCounter() {
            return this.globalCounter;
        }

        public long getNextIntSkip() {
            return this.nextIntSkip;
        }
    }

}
