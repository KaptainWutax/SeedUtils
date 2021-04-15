package kaptainwutax.seedutils.util;

import java.util.Iterator;
import java.util.function.LongConsumer;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

public class SeedIterator {

    protected final long min;
    protected final long max;

    protected long seed;
    protected LongUnaryOperator mapper;

    public SeedIterator(long min, long max) {
        this(min, max, LongUnaryOperator.identity());
    }

    public SeedIterator(long min, long max, LongUnaryOperator mapper) {
        this.min = min;
        this.max = max;
        this.mapper = mapper;
        this.seed = this.min;
    }

    public LongUnaryOperator getMapper() {
        return this.mapper;
    }

    public boolean hasNext() {
        return this.seed < this.max;
    }

    public long next() {
        return this.mapper.applyAsLong(this.seed++);
    }

    public void forEachRemaining(LongConsumer action) {
        while (this.hasNext()) {
            action.accept(this.next());
        }
    }

    public LongStream asStream() {
        return LongStream.range(this.min, this.max).map(this.mapper);
    }

    public LongStream streamRemaining() {
        return LongStream.range(this.seed, this.max).map(this.mapper);
    }

    public Iterator<Long> boxed() {
        return new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                return SeedIterator.this.hasNext();
            }

            @Override
            public Long next() {
                return SeedIterator.this.next();
            }
        };
    }

}
