package kaptainwutax.seedutils.mc.util;

import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.pos.BPos;

import java.util.Arrays;
import java.util.List;

public enum Rotation {

    NONE(Direction.NORTH),
    CLOCKWISE_90(Direction.EAST),
    CLOCKWISE_180(Direction.SOUTH),
    COUNTERCLOCKWISE_90(Direction.WEST)
    ;

    private final Direction direction;

    Rotation(Direction direction) {
        this.direction = direction;
    }

    public static Rotation getRandom(JRand rand) {
        return values()[rand.nextInt(values().length)];
    }

    public static List<Rotation> getShuffled(JRand rand) {
        List<Rotation> list = Arrays.asList(values());
        JRand.shuffle(list, rand);
        return list;
    }

    public Direction getDirection() {
        return direction;
    }

    public Rotation getRotated(Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180:
                switch (this) {
                    case NONE:
                        return CLOCKWISE_180;
                    case CLOCKWISE_90:
                        return COUNTERCLOCKWISE_90;
                    case CLOCKWISE_180:
                        return NONE;
                    case COUNTERCLOCKWISE_90:
                        return CLOCKWISE_90;
                }
            case COUNTERCLOCKWISE_90:
                switch (this) {
                    case NONE:
                        return COUNTERCLOCKWISE_90;
                    case CLOCKWISE_90:
                        return NONE;
                    case CLOCKWISE_180:
                        return CLOCKWISE_90;
                    case COUNTERCLOCKWISE_90:
                        return CLOCKWISE_180;
                }
            case CLOCKWISE_90:
                switch (this) {
                    case NONE:
                        return CLOCKWISE_90;
                    case CLOCKWISE_90:
                        return CLOCKWISE_180;
                    case CLOCKWISE_180:
                        return COUNTERCLOCKWISE_90;
                    case COUNTERCLOCKWISE_90:
                        return NONE;
                }
            default:
                return this;
        }
    }

    public Direction rotate(Direction direction) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return direction;
        } else {
            switch (this) {
                case CLOCKWISE_90:
                    return direction.getClockWise();
                case CLOCKWISE_180:
                    return direction.getOpposite();
                case COUNTERCLOCKWISE_90:
                    return direction.getCounterClockWise();
                default:
                    return direction;
            }
        }
    }

    public BPos getSize(BPos size) {
        switch (this) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                return new BPos(size.getZ(), size.getY(), size.getX());
            default:
                return size;
        }
    }

    public int rotate(int anchor, int referent) {
        switch (this) {
            case CLOCKWISE_90:
                return (anchor + referent / 4) % referent;
            case CLOCKWISE_180:
                return (anchor + referent / 2) % referent;
            case COUNTERCLOCKWISE_90:
                return (anchor + referent * 3 / 4) % referent;
            default:
                return anchor;
        }
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "direction=" + direction +
                '}';
    }
}
