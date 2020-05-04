package kaptainwutax.seedutils.util;

import java.util.function.Consumer;

public class SeedIterator {

    private static final Mapper SELF = seed -> seed;

    protected final long min;
    protected final long max;

    protected long seed;
    protected Mapper mapper;

    public SeedIterator(long min, long max) {
        this(min, max, SELF);
    }

    public SeedIterator(long min, long max, Mapper mapper) {
        this.min = min;
        this.max = max;
        this.mapper = mapper;
        this.seed = this.min;
    }

    public boolean hasNext() {
        return this.seed < this.max;
    }

    public long next() {
        return this.mapper.map(this.seed++);
    }

    public void forEachRemaining(Consumer<Long> action) {
        while(this.hasNext()) {
            action.accept(this.next());
        }
    }

    @FunctionalInterface
    public interface Mapper {
        long map(long seed);
    }

}
